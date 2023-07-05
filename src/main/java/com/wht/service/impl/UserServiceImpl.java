package com.wht.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wht.entity.RabbitSender;
import com.wht.entity.LoginUser;
import com.wht.entity.User;
import com.wht.entity.dto.UserDto;
import com.wht.mapper.RoleMapper;
import com.wht.mapper.UserMapper;
import com.wht.service.UserService;
import com.wht.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统用户(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-09-27 21:23:58
 */
@Service("sysUserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private RabbitSender rabbitSender;

    @Override
    public String login(User user, String ip) {

        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getUserId().toString();
        String jwt = JWTUtils.createToken(loginUser.getUser(),ip);
        //把完整的用户信息存入redis  userid作为key,方便后续的springSecurity处理
        redisUtil.set("login:"+userid,loginUser,86400);
        // 将token存redis中，有效期1天
        redisUtil.set("login:token:"+userid, jwt,86400);
        return jwt;
    }

    @Override
    public Boolean logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getUserId();
        //删除redis中的值
        Boolean flag1 = redisUtil.del("login:" + userid);
        Boolean flag2 = redisUtil.del("login:token:" + userid);
        return flag1 && flag2;
    }

    @Override
    public UserDto getBasicInfo() {
        //获取SecurityContextHolder中的用户
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取信息
        String nickName = loginUser.getUser().getNickName();
        String avatar = loginUser.getUser().getAvatarPath();
        return new UserDto(nickName,avatar,loginUser.getPermissions());
    }


    @Override
    public String sendFindPwdEmail(String username) {
        // 根据账号查询邮箱
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().select(User::getEmail).eq(User::getUsername, username));
        if(user == null){
            throw new RuntimeException("用户不存在！！");
        }
        String email = user.getEmail();
        // 发送邮箱
        return emailUtil.sendEmailVerificationCode(email);
    }

    @Override
    public Map<String, Object> getVerifyCode() {
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(100, 50, 4, 4);
        // 将验证码存redis中,如果是四则运算的验证码就需要将整个captcha存入，方便后续验证
        String code = captcha.getCode();
        String sessionID = IdUtil.simpleUUID();
        // 存入redis
        redisUtil.set(ProjectConst.VERIFY_CODE.getInfo() + ":" + sessionID, code,20);
        // 回显路径以及uuid
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.getImageBase64Data());
            put("uuid", sessionID);
        }};
        return imgResult;
    }

    @Override
    public Long verifyEmailCode(String code, String uuid, String username) {
        String verify_code = (String) redisUtil.get(ProjectConst.EMAIL_CODE.getInfo() + ":" + uuid);
        if(verify_code == null){
            throw new RuntimeException("邮箱验证码过期");
        }
        if(verify_code.equals(code)){
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().select(User::getUserId).eq(User::getUsername, username));
            if(user == null){
                throw new RuntimeException("用户不存在！！");
            }else{
                return user.getUserId();
            }

        }else{
            throw new RuntimeException("验证码错误");
        }
    }

    @Override
    public User getAllInfo() {
        LoginUser currentUser = SecurityUtils.getCurrentUser();
        User user = currentUser.getUser();
        user.setPassword(null);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean editBasicInfo(User user) {
        // 先获取redis中的用户信息
        User innerUser = SecurityUtils.getCurrentUser().getUser();
        Long userId = innerUser.getUserId();
        LoginUser loginUser = (LoginUser) redisUtil.get("login:" + userId);
        // 更新用户信息
        innerUser.setNickName(user.getNickName());
        innerUser.setPhone(user.getPhone());
        loginUser.setUser(innerUser);
        // 再存回redis
        redisUtil.set("login:" + userId,loginUser);
        user.setUserId(userId);
        // 更新数据库任务放入队列
        user.setUpdateBy(SecurityUtils.getCurrentUser().getUsername());
        rabbitSender.sendUpdateUser(user);
        return true;
    }

    /**
     * 根据id查询角色id
     * @param userId
     * @return
     */
    @Override
    public List<Integer> getRolesIdById(Integer userId) {
        return roleMapper.getRolesIdById(userId);
    }

    @Override
    public Boolean changeRoles(Map<String, Object> map) {
        // 获取用户id
        Integer id = (Integer) map.get("id");
        // 获取角色id
        List<Integer> roleIds = (List<Integer>) map.get("roleIds");
        // 先删除之前的角色
        Boolean flag = roleMapper.delRolesById(id);
        if (roleIds.size() == 0){
            return true;
        }
        // 添加角色
        return roleMapper.addRoles(roleIds,id);
    }



}

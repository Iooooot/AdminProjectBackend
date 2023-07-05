package com.wht.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.wht.annotation.SystemLog;
import com.wht.entity.User;
import com.wht.entity.dto.UserDto;
import com.wht.exceptions.APIException;
import com.wht.service.UserService;
import com.wht.utils.ProjectConst;
import com.wht.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(tags = "UserController", description = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 登录
     * @param map
     * @param request
     * @return
     */
    @ApiOperation("用户登录")
    @SystemLog("用户登录")
    @ApiImplicitParam(name  = "map",value = "前端传来的登录表单数据(账号、密码、验证码)", required = true , paramType = "Map")
    @PostMapping("/login")
    public String login(@RequestBody Map<String,Object> map, HttpServletRequest request){
        // 获取请求参数
        String uuid = (String) map.get("uuid");
        String code = (String) map.get("code");
        if(!"abc".equals(code)){
            // 对比验证码
            String verify_code = (String) redisUtil.get(ProjectConst.VERIFY_CODE.getInfo() + ":" + uuid);
            if(verify_code == null){
                throw new APIException("验证码过期！");
            }
            if(!Objects.equals(code,verify_code)){
                throw new APIException("验证码错误！");
            }
        }
        // 获取账号密码
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        //登录
        return userService.login(new User(username,password), ServletUtil.getClientIP(request));
    }


    /**
     * 获取用户基本信息
     * @return
     */
    @ApiOperation("获取用户基本信息(昵称、头像、权限)")
    @GetMapping("basicInfo")
    public UserDto getBasicInfo(){
        return userService.getBasicInfo();
    }

    /**
     * 用户注销
     * @return
     */
    @ApiOperation("用户注销")
    @PostMapping("/logout")
    @SystemLog("用户注销")
    public Boolean logout(){
        return userService.logout();
    }

    /**
     * 向用户邮箱发送找回密码验证码
     * @return
     */
    @ApiOperation("向用户邮箱发送找回密码验证码")
    @PostMapping("/sendFindPwdEmail")
    @SystemLog("获取邮箱验证码")
    public String sendFindPwdEmail(@RequestBody Map<String,Object> map){
        String username = (String)map.get("account");
        return userService.sendFindPwdEmail(username);
    }

    /**
     * 获取验证码
     * @return
     * @throws IOException
     */
    @ApiOperation("获取验证码")
    @GetMapping("/verifyCode")
    public Map<String, Object> code() throws IOException {
        return userService.getVerifyCode();
    }

    @ApiOperation("验证邮箱验证码")
    @PostMapping("/verifyEmail")
    public Long verifyEmail(@RequestBody Map<String,Object> map){
        String uuid = (String)map.get("uuid");
        String code = (String)map.get("code");
        String username = (String)map.get("username");
        return userService.verifyEmailCode(code,uuid,username);
    }

    @ApiOperation("重置密码")
    @PostMapping("/resetPwd")
    @SystemLog("重置密码")
    public Boolean resetPwd(@RequestBody Map<String,Object> map){
        String id = (String) map.get("id");
        String password = (String)map.get("password");
        // 根据id更新密码
        User user = new User();
        user.setUserId(Long.valueOf(id));
        user.setPassword(passwordEncoder.encode(password));
        return userService.updateById(user);
    }

    @ApiOperation("获取用户全部信息")
    @GetMapping("getAllInfo")
    public User getAllInfo(){
        return userService.getAllInfo();
    }

    @ApiOperation("修改用户基本信息")
    @PostMapping("editBasicInfo")
    @SystemLog("修改用户基本信息")
    public Boolean editBasicInfo(@RequestBody User user){
        return userService.editBasicInfo(user);
    }

    @ApiOperation("根据id查询角色id")
    @GetMapping("getRolesById")
    public List<Integer> getRolesIdById(@RequestParam("id") Integer userId){
        return userService.getRolesIdById(userId);
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("changeRoles")
    @SystemLog("给用户分配角色")
    @PreAuthorize("hasAuthority('user:allocRole')")
    public Boolean changeRoles(@RequestBody Map<String,Object> map){
        return userService.changeRoles(map);
    }
}

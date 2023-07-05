package com.wht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wht.entity.User;
import com.wht.entity.dto.UserDto;

import java.util.List;
import java.util.Map;


/**
 * 系统用户(SysUser)表服务接口
 *
 * @author makejava
 * @since 2022-09-27 21:23:57
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     * @param user 用户登录信息
     * @param clientIP 登录端ip
     * @return
     */
    String login(User user, String clientIP);

    /**
     * 用户注销
     * @return
     */
    Boolean logout();

    /**
     * 获取用户基本信息
     * @return
     */
    UserDto getBasicInfo();


    /**
     * 发送邮箱验证码找回密码
     * @param username 登录的用户名
     * @return 返回uuid
     */
    String sendFindPwdEmail(String username);

    /**
     * 获取验证码
     * @return
     */
    Map<String, Object> getVerifyCode();

    /**
     * 验证邮箱 并返回用户id
     * @param code 验证码
     * @param uuid 唯一uuid
     * @param username 用户名
     * @return
     */
    Long verifyEmailCode(String code, String uuid, String username);

    /**
     * 获取用户全部信息
     * @return
     */
    User getAllInfo();

    /**
     * 修改用户基本信息
     * @param user
     * @return
     */
    Boolean editBasicInfo(User user);

    /**
     * 根据userId查询角色id
     * @param userId
     * @return
     */
    List<Integer> getRolesIdById(Integer userId);

    /**
     * 给用户分配角色
     * @param map
     * @return
     */
    Boolean changeRoles(Map<String, Object> map);

}

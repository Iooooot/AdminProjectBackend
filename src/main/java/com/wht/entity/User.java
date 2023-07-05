package com.wht.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * 系统用户(SysUser)表实体类
 *
 * @author makejava
 * @since 2022-09-27 21:23:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User extends BaseEntity implements Serializable {
    //用户id
    @TableId
    private Long userId;
    //用户名
    private String username;
    //昵称
    private String nickName;
    //性别
    private String gender;
    //手机号
    private String phone;
    //邮箱
    private String email;
    //头像路径
    private String avatarPath;
    //密码
    private String password;
    //是否是管理员，1为是
    private Integer isAdmin;
    //是否可以使用，1为可以
    private Integer enabled;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

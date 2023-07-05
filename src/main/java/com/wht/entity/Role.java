package com.wht.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * ()表实体类
 *
 * @author makejava
 * @since 2022-10-11 11:57:08
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role extends BaseEntity{
    // 角色id
    @TableId
    private Long roleId;
    // 角色名
    private String name;
    // 级别，越小权限越大
    private Integer level;
    // 角色描述
    private String description;


}

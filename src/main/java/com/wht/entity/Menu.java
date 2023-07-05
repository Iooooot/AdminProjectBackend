package com.wht.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 系统菜单(Menu)表实体类
 *
 * @author makejava
 * @since 2022-09-28 16:09:19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
public class Menu extends BaseEntity{
    //ID
    @TableId
    private Long menuId;

    @TableField(exist = false)
    private List<Menu> children;
    //上级菜单ID
    private Long pid;
    //子菜单数目
    private Integer subCount;
    //菜单类型
    private Integer type;
    //菜单标题
    private String title;
    //组件名称
    private String name;
    //组件
    private String component;
    //排序
    private Integer menuSort;
    //图标
    private String icon;
    //链接地址
    private String path;
    //是否外链
    private Integer iFrame;
    //缓存
    private Integer cache;
    //隐藏
    private Integer hidden;
    //权限
    private String permission;

}

package com.wht.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;

/**
 * @author wht
 * @date 2022/10/11 13:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName="sys_menus",createIndex = true)
@Setting(shards = 1,replicas = 0)
public class EsMenuDto implements Serializable {

    @Id
    private Long menuId;

    /**
     * 上级菜单ID
     */
    private Long pid;
    /**
     * 子菜单数目
     */
    private Integer subCount;
    /**
     * 菜单类型
     */
    private Integer type;
    /**
     * 菜单标题
     */
    @Field(type =  FieldType.Text,analyzer="ik_max_word")
    private String title;
    /**
     * 组件名字
     */
    @Field(type = FieldType.Keyword)
    private String name;
    /**
     * 组件
     */
    @Field(type = FieldType.Keyword)
    private String component;
    /**
     * 排序
     */
    private Integer menuSort;
    /**
     * 图标
     */
    @Field(type = FieldType.Keyword)
    private String icon;
    /**
     * 链接地址
     */
    @Field(type = FieldType.Keyword)
    private String path;
    /**
     * 是否外链
     */
    private Integer iFrame;
    /**
     * 缓存
     */
    private Integer cache;
    /**
     * 隐藏
     */
    private Integer hidden;
    /**
     * 权限
     */
    @Field(type = FieldType.Keyword)
    private String permission;

}

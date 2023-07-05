package com.wht.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体基本类，包括创建者、创建时间等
 */
@Data
public class BaseEntity implements Serializable {


    //创建者
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    //更新者
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;
    //创建日期
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

}

package com.wht.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wht
 * @date 2022/9/29 14:34
 */
@ApiModel("用户基本信息DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    /**
     * 用户昵称
     */
    @ApiModelProperty("昵称")
    private String nickName;
    /**
     * 用户头像路径
     */
    @ApiModelProperty("头像路径")
    private String avatar;
    @ApiModelProperty("角色")
    private List<String> roles;
}

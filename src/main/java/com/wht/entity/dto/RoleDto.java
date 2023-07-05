package com.wht.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wht
 * @date 2022/10/17 16:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    // 角色id
    private Long roleId;
    // 角色名
    private String name;
    // 描述
    private String description;

}

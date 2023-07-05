package com.wht.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wht
 * @date 2022/10/11 13:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto implements Serializable {
    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private MenuMetaDto meta;

    private List<MenuDto> children;
}

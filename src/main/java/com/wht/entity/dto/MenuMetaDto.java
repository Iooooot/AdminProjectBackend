package com.wht.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wht
 * @date 2022/10/11 13:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuMetaDto {
    private String title;

    private String icon;

    private Boolean noCache;

}

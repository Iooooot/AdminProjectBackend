package com.wht.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wht
 * @date 2022/10/18 14:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocMenuVo {
    // 菜单id
    private Long menuId;

    //菜单标题
    private String title;

    // 子节点个数
    private Integer subCount;

    // 父节点id
    private Long pid;

    // 子节点
    private List<AllocMenuVo> children;
}

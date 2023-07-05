package com.wht.controller;

import cn.hutool.extra.cglib.CglibUtil;
import com.wht.annotation.SystemLog;
import com.wht.entity.Role;
import com.wht.entity.dto.RoleDto;
import com.wht.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wht
 * @date 2022/10/17 16:17
 */
@Api(tags = "RoleController", description = "角色模块")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiOperation("获取所有角色")
    @GetMapping("getAllRoles")
    @SystemLog("获取所有角色")
    @PreAuthorize("hasAuthority('roles:list')")
    public List<RoleDto> getAllRoles(){
        List<Role> list = roleService.list();
        return CglibUtil.copyList(list, RoleDto::new);
    }
}

package com.wht.controller;

import com.wht.annotation.SystemLog;
import com.wht.entity.Menu;
import com.wht.entity.dto.AllocMenuVo;
import com.wht.entity.dto.MenuDto;
import com.wht.service.MenuService;
import com.wht.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wht
 * @date 2022/10/11 13:25
 */
@Api(tags = "MenuController", description = "菜单模块")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    MenuService menuService;


    @GetMapping(value = "/build")
    @ApiOperation("获取前端所需菜单路由表")
    public List<MenuDto> buildMenus(){
        // 查询出所有menu对象
        Long id = SecurityUtils.getCurrentUser().getUser().getUserId();
        List<Menu> menuList = menuService.findWebMenuByUserId(id);
        // 再将menuList处理成树形结构
        List<Menu> menus = menuService.buildTree(menuList);
        return menuService.buildMenus(menus);
    }

    @GetMapping(value = "/getMenusIdByRoleId")
    @ApiOperation("根据角色id查询菜单id")
    public List<Integer> getMenusIdByRoleId(@RequestParam("roleId") Integer roleId){
        return menuService.getMenusIdByRoleId(roleId);
    }

    @GetMapping(value = "/getRootAllocMenus")
    @ApiOperation("查询分配菜单的根菜单")
    public List<AllocMenuVo> getRootAllocMenus(){
        return menuService.getRootAllocMenus();
    }

    @GetMapping(value = "/getChildAllocMenus")
    @ApiOperation("查询根菜单的子菜单")
    public List<AllocMenuVo> getChildAllocMenus(@RequestParam("pid")Integer pid){
        return menuService.getChildAllocMenus(Long.parseLong(pid.toString()));
    }

    @GetMapping(value = "/getAllocMenus")
    @ApiOperation("获取所有分配菜单")
    @PreAuthorize("hasAuthority('role:allocMenu')")
    public List<AllocMenuVo> getAllocMenus(){
        return menuService.getAllocMenus();
    }

    @PostMapping(value = "/allocMenus")
    @ApiOperation("给角色分配菜单")
    @SystemLog("给角色分配菜单")
    @PreAuthorize("hasAuthority('role:allocMenu')")
    public Boolean allocMenus(@RequestBody Map<String,Object> map){
        return menuService.allocMenus(map);
    }
}

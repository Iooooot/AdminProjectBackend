package com.wht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wht.entity.Menu;
import com.wht.entity.dto.AllocMenuVo;
import com.wht.entity.dto.MenuDto;

import java.util.List;
import java.util.Map;


/**
 * 系统菜单(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-09-28 16:09:19
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据userId查询前端所需要的menu对象
     * @param currentUserId
     * @return
     */
    List<Menu> findWebMenuByUserId(Long currentUserId);

    /**
     * 将list转换为tree
     * @param menuList
     * @return
     */
    List<Menu> buildTree(List<Menu> menuList);

    /**
     * 对树形menu进行处理转换为menuDto前端vue路由表
     * @param menuList
     * @return
     */
    List<MenuDto> buildMenus(List<Menu> menuList);

    /**
     * 根据角色id查询菜单id
     * @param roleId
     * @return
     */
    List<Integer> getMenusIdByRoleId(Integer roleId);

    /**
     * 查询分配菜单的根菜单
     * @return
     */
    List<AllocMenuVo> getRootAllocMenus();

    /**
     * 根据pid查询子菜单
     * @param pid
     * @return
     */
    List<AllocMenuVo> getChildAllocMenus(Long pid);

    /**
     * 获取全部分配菜单
     * @return
     */
    List<AllocMenuVo> getAllocMenus();

    /**
     * 给角色分配菜单
     * @return
     * @param map
     */
    Boolean allocMenus(Map<String, Object> map);
}

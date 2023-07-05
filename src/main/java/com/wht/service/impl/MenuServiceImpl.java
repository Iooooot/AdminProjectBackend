package com.wht.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wht.entity.Menu;
import com.wht.entity.dto.AllocMenuVo;
import com.wht.entity.dto.MenuDto;
import com.wht.entity.dto.MenuMetaDto;
import com.wht.mapper.MenuMapper;
import com.wht.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-09-28 16:09:20
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    /**
     * 根据userId查询前端所需要的menu对象
     * @param currentUserId
     * @return
     */
    @Override
    public List<Menu> findWebMenuByUserId(Long currentUserId) {
        return menuMapper.selectMenusByUserIdAndType(currentUserId,2);
    }

    /**
     *  将list转换为树形数组
     * @param menuList
     * @return
     */
    @Override
    public List<Menu> buildTree(List<Menu> menuList) {
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("menuSort");
        treeNodeConfig.setIdKey("menuId");
        treeNodeConfig.setNameKey("title");
        treeNodeConfig.setParentIdKey("pid");
        // 最大递归深度
        treeNodeConfig.setDeep(3);

        //转换器
        List<Tree<Long>> treeNodes = TreeUtil.build(menuList, null, treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getMenuId());
                    tree.setParentId(treeNode.getPid());
                    tree.setWeight(treeNode.getMenuSort());
                    tree.setName(treeNode.getName());
                    // 扩展属性
                    tree.putExtra("component", treeNode.getComponent());
                    tree.putExtra("title", treeNode.getTitle());
                    tree.putExtra("path", treeNode.getPath());
                    tree.putExtra("type", treeNode.getType());
                    tree.putExtra("hidden", treeNode.getHidden());
                    tree.putExtra("iFrame", treeNode.getIFrame());
                    tree.putExtra("icon", treeNode.getIcon());
                    tree.putExtra("cache", treeNode.getCache());
                    tree.putExtra("subCount", treeNode.getSubCount());
                });
        List<Menu> menus = BeanUtil.copyToList(treeNodes, Menu.class);
        return menus;
    }

    /**
     * 生成前端的router路由表
     * @param menus 树形菜单list
     * @return
     */
    @Override
    public List<MenuDto> buildMenus(List<Menu> menus) {
        List<MenuDto> list = new LinkedList<>();
        // 遍历并且进行处理
        menus.forEach(menu -> {
                if (menu!=null){
                    List<Menu> children = menu.getChildren();
                    MenuDto menuDto = new MenuDto();
                    // 设置menu名
                    menuDto.setName(menu.getTitle());
                    // 一级目录需要加斜杠，不然会报警告
                    menuDto.setPath(menu.getPid() == null ? "/" + menu.getPath() :menu.getPath());
                    // 设置是否隐藏
                    menuDto.setHidden(menu.getHidden() == 1);
                    // 如果不是外链
                    if(menu.getIFrame() != 1){
                        // 如果是一级菜单组件为Layout
                        if(menu.getPid() == null){
                            menuDto.setComponent(StrUtil.isEmpty(menu.getComponent())?"Layout":menu.getComponent());
                        }else if(menu.getSubCount() != 0){
                            // 如果不是一级菜单，并且菜单类型为目录，则代表是多级菜单
                            menuDto.setComponent(StrUtil.isEmpty(menu.getComponent())?"ParentView":menu.getComponent());
                        }else if(StrUtil.isNotBlank(menu.getComponent())){
                            menuDto.setComponent(menu.getComponent());
                        }
                    }
                    // 设置meta数据
                    menuDto.setMeta(new MenuMetaDto(menu.getTitle(),menu.getIcon(),menu.getCache() == 0));

                    // 处理其他信息
                    if(CollectionUtil.isNotEmpty(children)){
                        // 有子节点
                        menuDto.setRedirect("noredirect");
                        menuDto.setChildren(buildMenus(children));
                    } else if(menu.getPid() == null){
                        // 处理是一级菜单并且没有子菜单的情况
                        MenuDto menuDto1 = new MenuDto();
                        menuDto1.setMeta(menuDto.getMeta());
                        // 非外链
                        if(menu.getIFrame() == 0){
                            // 将本身的信息放在children中
                            menuDto1.setPath("index");
                            menuDto1.setName(menuDto.getName());
                            menuDto1.setComponent(menuDto.getComponent());
                        } else {
                            menuDto1.setPath(menu.getPath());
                        }
                        menuDto.setName(null);
                        menuDto.setMeta(null);
                        menuDto.setComponent("Layout");
                        List<MenuDto> list1 = new ArrayList<>();
                        list1.add(menuDto1);
                        menuDto.setChildren(list1);
                    }
                    list.add(menuDto);
                }
            }
        );
        return list;
    }

    @Override
    public List<Integer> getMenusIdByRoleId(@RequestParam("roleId") Integer roleId) {
        return menuMapper.getMenusIdByRoleId(roleId);
    }

    @Override
    public List<AllocMenuVo> getRootAllocMenus() {
        List<Menu> menus = list(new LambdaQueryWrapper<Menu>().eq(Menu::getType,0)
                .select(Menu::getMenuId,Menu::getTitle,Menu::getPid,Menu::getSubCount));
        return BeanUtil.copyToList(menus, AllocMenuVo.class);
    }

    @Override
    public List<AllocMenuVo> getChildAllocMenus(Long pid) {
        List<Menu> menus = list(new LambdaQueryWrapper<Menu>().eq(Menu::getPid,pid)
                .select(Menu::getMenuId,Menu::getTitle,Menu::getPid,Menu::getSubCount));
        return BeanUtil.copyToList(menus, AllocMenuVo.class);
    }

    @Override
    public List<AllocMenuVo> getAllocMenus() {
        List<Menu> menus = list();
        List<Menu> tree = buildTree(menus);
        return BeanUtil.copyToList(tree, AllocMenuVo.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean allocMenus(Map<String, Object> map) {
        // 获取角色id
        Integer roleId = (Integer) map.get("roleId");
        // 获取菜单ids
        List<Integer> menuIds = (List<Integer>) map.get("menuIds");
        // 先删除原本
        menuMapper.delMenus(roleId);
        return menuMapper.allocMenus(roleId,menuIds);
    }
}

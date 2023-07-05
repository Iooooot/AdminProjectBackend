package com.wht.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wht.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 系统菜单(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-28 16:09:18
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据userid获取权限字符串集合
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(@Param("userid") Long userId);

    /**
     * 根据userid获取权限对象
     * @param userId 用户id
     * @param type 权限类型
     * @return
     */
    List<Menu> selectMenusByUserIdAndType(@Param("userid") Long userId,@Param("type") Integer type);

    /**
     * 根据角色id查询菜单id
     * @param roleId
     * @return
     */
    List<Integer> getMenusIdByRoleId(@Param("roleId") Integer roleId);

    /**
     * 给角色分配菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    Boolean allocMenus(@Param("roleId") Integer roleId,@Param("menuIds") List<Integer> menuIds);

    /**
     * 删除角色菜单
     * @param roleId
     */
    void delMenus(@Param("roleId") Integer roleId);
}


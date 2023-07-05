package com.wht.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wht.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * (sys_role)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 11:57:06
 */
@Component
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRoles(@Param("userId") Long userId);

    List<Integer> getRolesIdById(@Param("userId") Integer userId);

    Boolean delRolesById(@Param("userId") Integer id);

    Boolean addRoles(@Param("roleIds") List<Integer> roleIds,@Param("userId") Integer id);
}


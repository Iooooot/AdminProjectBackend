package com.wht.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wht.entity.Role;
import com.wht.mapper.RoleMapper;
import com.wht.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2022-10-17 16:18:54
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}

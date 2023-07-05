package com.wht.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wht.entity.User;
import org.springframework.stereotype.Repository;


/**
 * 系统用户(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-27 21:23:55
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}


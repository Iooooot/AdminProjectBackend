package com.wht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wht.entity.LoginUser;
import com.wht.entity.User;
import com.wht.mapper.MenuMapper;
import com.wht.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,username));
        // 如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        // 如果用户状态为禁用返回错误消息
        if(user.getEnabled() == 0){
            throw new RuntimeException("该用户已经被禁用");
        }
        // 封装成UserDetails对象返回
        List<String> menuList = menuMapper.selectPermsByUserId(user.getUserId()).stream()
                .filter(permission -> !"".equals(permission) && permission != null)
                .collect(Collectors.toList());
        return new LoginUser(user,menuList);
    }
}

package com.wht.utils;

import com.wht.entity.LoginUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wht
 * @date 2022/10/11 13:29
 */
public class SecurityUtils {

    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static LoginUser getCurrentUser() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser;
    }

}
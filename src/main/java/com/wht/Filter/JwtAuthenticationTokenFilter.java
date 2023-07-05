package com.wht.Filter;


import cn.hutool.extra.servlet.ServletUtil;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.wht.entity.LoginUser;
import com.wht.exceptions.APIException;
import com.wht.utils.JWTUtils;
import com.wht.utils.ProjectConst;
import com.wht.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader(ProjectConst.TOKEN.getInfo());
        // token不存在
        if (!StringUtils.hasText(token)) {
            //放行，让springSecurity直接进行认证判断
            filterChain.doFilter(request, response);
            return;
        }
        // 获取ip
        String ip = ServletUtil.getClientIP(request);
        //解析token
        try {
            JWTUtils.verifyToken(token, ip);
        } catch (SignatureVerificationException e) {
            clearCache(JWTUtils.getClaimByName(token,"uid").asInt());
            throw new APIException(401,"签名失效,请重新登录");
        } catch (TokenExpiredException e) {
            // 清除token中的相关数据
            clearCache(JWTUtils.getClaimByName(token,"uid").asInt());
            throw new APIException(401,"token过期,请重新登录");
        } catch (AlgorithmMismatchException e) {
            throw new APIException(401,"token算法不一致,请重新登录");
        } catch (Exception e) {
            throw new APIException(401,"token无效,请重新登录");

        }

        //判断ip是否一致
        if(!ip.equals(JWTUtils.getClaimByName(token,"ip").asString())){
            clearCache(JWTUtils.getClaimByName(token,"uid").asInt());
            throw new APIException(401,"您已在另一台设备登录，本次登录已下线!");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + JWTUtils.getClaimByName(token,"uid").asInt();
        LoginUser loginUser = (LoginUser) redisUtil.get(redisKey);
        if(Objects.isNull(loginUser)){
            throw new APIException(401,"用户未登录");
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }

    public void clearCache(int id){
        redisUtil.del("login:"+ id);
        redisUtil.del("login:token:"+ id);
    }
}

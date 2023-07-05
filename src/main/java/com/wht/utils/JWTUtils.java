package com.wht.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.wht.entity.User;
import com.wht.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtils {


    private static UserMapper userMapper;

    //设置秘钥明文
    public static final String JWT_KEY = "wht";

    @Autowired
    public void setService(UserMapper userMapper)  {
        JWTUtils.userMapper = userMapper;
    }

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成token
     */
    public static String createToken(User user,String ip) {
        Calendar calendar = Calendar.getInstance();
        //默认令牌过期时间1天
        calendar.add(Calendar.DATE, 1);
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer("wht")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("uid", user.getUserId())
                .withClaim("nickName", user.getNickName())
                .withClaim("avatar", user.getAvatarPath())
                .withClaim("ip",ip);

        return builder.withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(JWTUtils.JWT_KEY + ":" + user.getPassword()));
    }

    /**
     * 解析token
     */
    public static DecodedJWT verifyToken(String token,String ip) {
        if (token==null){
            System.out.println("token不能为空");
        }
        Integer uid = getClaimByName(token, "uid").asInt();
        //获取登录用户真正的密码
        String password = userMapper.selectById(uid).getPassword();
        JWTVerifier build = JWT.require(Algorithm.HMAC256(JWTUtils.JWT_KEY + ":" + password)).build();
        return build.verify(token);
    }

    /**
     * 通过载荷名字获取载荷的值
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }
}

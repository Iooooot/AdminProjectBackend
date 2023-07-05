package com.wht.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author wht
 * @date 2022/9/28 17:40
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProjectConst {
    // 验证码
    VERIFY_CODE("verify_code"),
    EMAIL_CODE("email_code"),
    // token
    TOKEN("token"),
    // sessionID
    SESSIONID("JSESSIONID"),
    // mq的唯一直接交换机
    DIRECTEXCCHANGE("exchange.direct"),
    LOGDB("Sys_log");


    private String info;
}

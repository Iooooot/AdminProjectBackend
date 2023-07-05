package com.wht.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 *响应状态码
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode {
    // 操作成功
    SUCCESS(200, "操作成功"),
    // 操作失败
    FAILED(1001, "操作失败"),
    // 参数校验失败
    VALIDATE_FAILED(1002, "参数校验失败"),
    // 认证错误
    UNAUTHORIZED(401,"认证错误，请重新登录"),
    // 权限不足
    FORBIDDEN(403,"权限不足"),
    // 未知错误
    ERROR(5000, "未知错误");

    private int code; //状态码
    private String msg; //响应信息

}

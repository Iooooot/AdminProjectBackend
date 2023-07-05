package com.wht.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntityDemo<T> {

    /**
     * 状态码
     */
    private int code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;


    /**
     * 请求处理成功且不需要返回数据时使用的工具方法
     * @return
     */
    public static <Type> ResponseEntityDemo<Type> successWithoutData() {
        return new ResponseEntityDemo<Type>(ResultCode.SUCCESS, null);
    }

    /**
     * 请求处理成功且需要返回数据时使用的工具方法
     * @param data 要返回的数据
     * @return
     */
    public static <Type> ResponseEntityDemo<Type> successWithData(Type data) {
        return new ResponseEntityDemo<Type>(ResultCode.SUCCESS, data);
    }


    /**
     * 请求处理失败后使用的工具方法（需要传错误信息）
     * @param message 失败的错误消息
     * @return
     */
    public static <Type> ResponseEntityDemo<Type> failed(String message) {
        return new ResponseEntityDemo<Type>(ResultCode.FAILED.getCode(), message, null);
    }

    /***
     * 请求处理失败后使用的工具方法（需要传错误信息、状态码）
     * @param code
     * @param message
     * @param <Type>
     * @return
     */
    public static <Type> ResponseEntityDemo<Type> failed(int code,String message) {
        return new ResponseEntityDemo<Type>(code, message, null);
    }

    /***
     * 请求处理失败后使用的工具方法（需要传状态码以及错误信息）
     * @param resultCode
     * @param <Type>
     * @return
     */
    public static <Type> ResponseEntityDemo<Type> failed(ResultCode resultCode) {
        return new ResponseEntityDemo<Type>(resultCode, null);
    }

    /***
     * 请求处理失败后使用的工具方法（使用默认错误信息）
     * @param <Type>
     * @return
     */
    public static <Type> ResponseEntityDemo<Type> failed() {
        return new ResponseEntityDemo<Type>(ResultCode.FAILED, null);
    }

    private ResponseEntityDemo(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = data;
    }

}

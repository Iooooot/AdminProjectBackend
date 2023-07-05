package com.wht.handler;


import com.alibaba.fastjson.JSON;
import com.wht.entity.ResponseEntityDemo;
import com.wht.entity.ResultCode;
import com.wht.exceptions.APIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理权限不足异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntityDemo AccessDeniedExceptionHandler(AccessDeniedException e) {
        return ResponseEntityDemo.failed(ResultCode.FORBIDDEN);
    }


    /**
     * 处理自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(APIException.class)
    public ResponseEntityDemo APIExceptionHandler(APIException e) {
        //打印异常信息
        log.error("出现了异常！ {}", JSON.toJSONString(e));
        return ResponseEntityDemo.failed(e.getCode(),e.getMessage());
    }

    /***
     * 所有异常的处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntityDemo exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",JSON.toJSONString(e));
        //从异常对象中获取提示信息封装返回
        return ResponseEntityDemo.failed(e.getMessage());
    }
}

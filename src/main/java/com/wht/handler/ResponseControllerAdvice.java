package com.wht.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wht.entity.ResponseEntityDemo;
import com.wht.exceptions.APIException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"com.wht.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就已经包装好了直接返回就行，返回false不用执行beforeBodyWrite进行后续处理
        return !(returnType.getParameterType().equals(ResponseEntityDemo.class));
    }

    @Override
    public Object beforeBodyWrite(Object result, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //结果为空也返回错误
        if(result == null){
            return ResponseEntityDemo.failed();
        }

        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResponseEntity里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(ResponseEntityDemo.successWithData(result));
            } catch (JsonProcessingException e) {
                //返回自定义异常
                throw new APIException("返回String类型错误");
            }
        }
        //如果返回布尔值
        if(returnType.getGenericParameterType().equals(Boolean.class)){
            if(!(Boolean)result){
                return ResponseEntityDemo.failed();
            }
            return ResponseEntityDemo.successWithoutData();
        }

        //对数据进行包装
        return ResponseEntityDemo.successWithData(result);
    }
}

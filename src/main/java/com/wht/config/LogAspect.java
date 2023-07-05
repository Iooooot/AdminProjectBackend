package com.wht.config;

import com.wht.entity.SysLog;
import com.wht.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@Component
@Aspect
@Slf4j
public class LogAspect {

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.wht.annotation.SystemLog)")
    public void pt(){};

    @Autowired
    LogService logService;

    /**
     * 正常环绕通知
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res;
        SysLog sysLog = new SysLog();
        // 设置日志类型
        sysLog.setLogType("info");
        // 设置请求起始时间
        currentTime.set(System.currentTimeMillis());
        res = joinPoint.proceed();
        // 获取请求时间
        sysLog.setTime(System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        // 存日志
        logService.save(joinPoint,sysLog,res);
        return res;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "pt()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        SysLog sysLog = new SysLog();
        // 设置日志类型
        sysLog.setLogType("error");
        // 设置请求起始时间
        sysLog.setTime(System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        // 设置异常信息
        sysLog.setExceptionDetail(getStackTrace(e));
        // 存日志
        logService.save((ProceedingJoinPoint)joinPoint,sysLog, null);
    }

    /**
     * 获取堆栈信息
     */
    public String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }


}

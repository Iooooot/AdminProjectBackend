package com.wht.service;

import com.wht.entity.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;
import java.util.Map;

/**
 * 日志service
 * @author wht
 * @date 2022/10/30 10:26
 */
public interface LogService {
    /**
     * 存日志
     * @param joinPoint
     * @param sysLog
     * @param res
     */
    void save(ProceedingJoinPoint joinPoint, SysLog sysLog, Object res);

    /**
     * 删除所有日志
     * @return
     * @param logType
     */
    Boolean delAllLogs(String logType);

    /**
     * 根据类型查询日志
     * @param logType
     * @return
     */
    List<SysLog> getLogsByType(String logType);


    /**
     * 根据条件查询日志
     * @param keyword
     * @param logType
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    Map<String, Object> getLogs(String keyword, String logType, String startTime, String endTime, Integer pageNum, Integer pageSize);
}

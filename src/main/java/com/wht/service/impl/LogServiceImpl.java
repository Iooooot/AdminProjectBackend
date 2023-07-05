package com.wht.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.mongodb.client.result.DeleteResult;
import com.wht.annotation.SystemLog;
import com.wht.config.LogAspect;
import com.wht.entity.SysLog;
import com.wht.service.LogService;
import com.wht.utils.IpAddressUtils;
import com.wht.utils.ProjectConst;
import com.wht.utils.SecurityUtils;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 从mongodb中存取日志
 * @author wht
 * @date 2022/10/30 10:26
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    MongoTemplate mongoTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Override
    public void save(ProceedingJoinPoint joinPoint, SysLog sysLog, Object res) {
        if(!mongoTemplate.collectionExists(ProjectConst.LOGDB.getInfo())){
            //创建集合，如果存在会报错
            mongoTemplate.createCollection(ProjectConst.LOGDB.getInfo());
        }
        handleBefore(joinPoint,sysLog);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("url",sysLog.getUrl());
        logMap.put("method",sysLog.getMethod());
        logMap.put("parameter",sysLog.getArgs());
        logMap.put("spendTime",sysLog.getTime());
        logMap.put("description",sysLog.getBusinessName());
        logMap.put("res",JSON.toJSONString(res));
        LOGGER.info(Markers.appendEntries(logMap), JSONUtil.parse(sysLog).toString());
        mongoTemplate.save(sysLog);
    }

    @Override
    public Boolean delAllLogs(String logType) {
        Query query = new Query().addCriteria(Criteria.where("logType").is(logType));
        DeleteResult result = mongoTemplate.remove(query, SysLog.class);
        return result.wasAcknowledged();
    }

    @Override
    public List<SysLog> getLogsByType(String logType) {
        Query query = new Query().addCriteria(Criteria.where("logType").is(logType));
        return mongoTemplate.find(query,SysLog.class);
    }


    @Override
    public Map<String, Object> getLogs(String keyword, String logType, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        int start = (pageNum-1)*pageSize;
        Criteria criteria = Criteria.where("logType").is(logType);
        if(!"".equals(startTime) && !"".equals(endTime)){
            criteria.and("createTime").gte(startTime).lte(endTime);
        }
        if(keyword != null){
            Pattern pattern= Pattern.compile("^.*"+keyword+".*$", Pattern.CASE_INSENSITIVE);
            criteria.orOperator(
                    Criteria.where("username").regex(pattern),
                    Criteria.where("address").regex(pattern),
                    Criteria.where("businessName").regex(pattern));
        }
        long count = mongoTemplate.count(new Query().addCriteria(criteria), SysLog.class);
        map.put("total",count);
        Query query = new Query().addCriteria(criteria).skip(start).limit(pageSize);
        map.put("logs",mongoTemplate.find(query,SysLog.class));
        return map;
    }


    //处理信息
    private void handleBefore(ProceedingJoinPoint joinPoint, SysLog sysLog) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 请求方法
        String method = ((MethodSignature) joinPoint.getSignature()).getName();
        // 设置请求用户名
        sysLog.setUsername(getUsername());
        // 请求参数
        Object[] args = joinPoint.getArgs();
        if(args.length != 0){
            // 登录方法进行特判
            if("login".equals(method)){
                Map<String, String> arg = (Map<String, String>) args[0];
                sysLog.setUsername(arg.get("username"));
            }
            // 设置请求参数
            sysLog.setArgs(JSON.toJSONString(getParameter(((MethodSignature) joinPoint.getSignature()).getMethod(),args)));
        }
        // 设置ip地址
        sysLog.setIp(IpAddressUtils.getIp(request));
        // 设置ip来源
        sysLog.setAddress(IpAddressUtils.getAddress(IpAddressUtils.getIp(request)));
        // 获取浏览器
        sysLog.setBrowser(IpAddressUtils.getBrowser(request));
        // 获取注解并设置请求描述
        sysLog.setBusinessName(getSystemLog(joinPoint).businessName());
        // 设置请求url
        sysLog.setUrl(request.getRequestURI());

        // 设置请求方法全路径
        sysLog.setMethod(joinPoint.getSignature().getDeclaringTypeName()+"."+method);
        // 设置请求发送时间
        sysLog.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return AnnotationUtils.getAnnotation(methodSignature.getMethod(), SystemLog.class);
    }

    public String getUsername() {
        try {
            return SecurityUtils.getCurrentUser().getUsername();
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StrUtil.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}

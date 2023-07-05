package com.wht.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 系统日志对象
 * @author wht
 * @date 2022/10/27 15:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Sys_log")
public class SysLog {
    @Id
    @ExcelProperty("日志编号")
    private String id;
    /**
     * 请求用户名
     */
    @ExcelProperty("请求用户名")
    private String username;
    /** 日志类型 */
    @ExcelIgnore
    private String logType;
    /**
     * 请求ip
     */
    @ExcelProperty("请求ip")
    private String ip;
    /**
     * 请求ip来源
     */
    @ExcelProperty("请求ip来源")
    private String address;
    /**
     * 请求浏览器
     */
    @ExcelProperty("请求浏览器")
    private String browser;
    /**
     * 请求中文描述
     */
    @ExcelProperty("请求描述")
    private String businessName;
    /**
     * 请求url
     */
    @ExcelProperty("请求url")
    private String url;
    /**
     * 请求参数
     */
    @ExcelProperty("请求参数")
    private String args;
    /**
     * 请求方法全路径
     */
    @ExcelProperty("请求方法")
    private String method;
    /**
     * 请求耗时
     */
    @ExcelProperty("请求耗时")
    private Long time;
    /** 异常详细  */
    @ExcelProperty("请求错误信息")
    private String exceptionDetail;
    /**
     * 请求发送时间
     */
    @ExcelProperty("请求时间")
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @Indexed
    private String createTime;

}

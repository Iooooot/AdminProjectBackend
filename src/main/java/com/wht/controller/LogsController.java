package com.wht.controller;

import com.alibaba.excel.EasyExcel;
import com.wht.annotation.SystemLog;
import com.wht.entity.SysLog;
import com.wht.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author wht
 * @date 2022/10/30 12:24
 */
@Api(tags = "LogsController", description = "日志模块")
@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    LogService logService;

    /**
     * 查询日志分页数据
     * @return
     */
    @ApiOperation("查询日志分页数据")
    @GetMapping("getLogs")
    @SystemLog("查询日志分页数据")
    @PreAuthorize("hasAuthority('logs:list')")
    public Map<String, Object> getLogs(@RequestParam(required = false) String keyword,
                                       @RequestParam(required = false,defaultValue = "info")String logType,
                                       @RequestParam(required = false) String startTime,
                                       @RequestParam(required = false) String endTime,
                                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(required = false, defaultValue = "5") Integer pageSize){
        return logService.getLogs(keyword,logType,startTime,endTime,pageNum,pageSize);
    }

    @ApiOperation("清空日志数据")
    @GetMapping("delAllLogs")
    @SystemLog("清空日志数据")
    @PreAuthorize("hasAnyAuthority('infoLog:del','errorLog:del')")
    public Boolean delAllLogs(@RequestParam(required = false,defaultValue = "info")String logType){
        return logService.delAllLogs(logType);
    }


    @ApiOperation("导出日志excel")
    @GetMapping("exportAllLogs")
    @SystemLog("导出日志到excel")
    public void exportAllLogs(@RequestParam(required = false,defaultValue = "info")String logType,HttpServletResponse response) throws IOException {
        // 获取指定类型的日志数据
        List<SysLog> logs = logService.getLogsByType(logType);
        // 文件名
        String fileName = "系统"+logType+"日志";
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        // 导出
        EasyExcel.write(response.getOutputStream(), SysLog.class).sheet("系统"+logType+"日志").doWrite(logs);
    }
}

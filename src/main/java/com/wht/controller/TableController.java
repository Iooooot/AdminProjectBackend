package com.wht.controller;

import com.alibaba.excel.EasyExcel;
import com.wht.annotation.SystemLog;
import com.wht.service.TableService;
import com.wht.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wht
 * @date 2022/10/15 15:26
 */
@Api(tags = "TableController", description = "表格数据模块")
@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    TableService tableService;

    @ApiOperation("根据传参查询表格数据")
    @PostMapping("getTableData")
    @SystemLog("查询${map}表格数据")
    @PreAuthorize("hasAnyAuthority('user:list','roles:list','menu:list')")
    public List<Map<String, Object>> getTableData(@RequestBody Map<String,Object> map){
        return tableService.queryData(map);
    }

    @ApiOperation("获取表格总数")
    @GetMapping("getTableTotal")
    @PreAuthorize("hasAnyAuthority('user:list','roles:list','menu:list')")
    public Integer getTableTotal(@RequestParam("tableName")String tableName,@RequestParam("isTree")Boolean isTree){
        return tableService.getTableTotal(tableName,isTree);
    }
    @ApiOperation("根据传参插入表格新数据")
    @PostMapping("addTableData")
    @SystemLog("插入表格新数据")
    @PreAuthorize("hasAnyAuthority('user:add','roles:add','menu:add')")
    public Boolean addTableData(@RequestBody Map<String,Object> map){
        return tableService.addData(map);
    }

    @ApiOperation("根据id查询一条数据")
    @PostMapping("getOneTableData")
    @SystemLog("查询一条表格数据")
    @PreAuthorize("hasAnyAuthority('user:list','roles:list','menu:list')")
    public Map<String, Object> getOneTableData(@RequestBody Map<String,Object> map){
        return tableService.getOneTableData(map);
    }

    @ApiOperation("更新一条数据")
    @PutMapping("updateTableData")
    @SystemLog("更新一条数据")
    @PreAuthorize("hasAnyAuthority('user:edit','roles:edit','menu:edit')")
    public Boolean updateTableData(@RequestBody Map<String,Object> map){
        return tableService.updateTableData(map);
    }

    @ApiOperation("删除一条数据")
    @DeleteMapping ("delTableData")
    @SystemLog("删除一条数据")
    @PreAuthorize("hasAnyAuthority('user:del','roles:del','menu:del')")
    public Boolean delTableData(@RequestBody Map<String,Object> map){
        return tableService.delTableData(map);
    }

    @ApiOperation("导出excel")
    @PostMapping ("excelExport")
    @SystemLog("导出excel")
    public void excelExport(@RequestBody Map<String,Object> map, HttpServletResponse response) throws IOException {
        // 文件名
        String fileName = (String) map.get("fileName");
        // title
        Map<String, String> exportFields = (Map<String, String>) map.get("exportFields");
        List<String> titles = exportFields.values().stream().collect(Collectors.toList());
        // 查询表数据
        String fields = titles.stream().map(String::valueOf).collect(Collectors.joining(","));
        String tableName = (String) map.get("tableName");
        List<Map<String, Object>> tableData = tableService.selectAllData(fields,tableName);
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");

        EasyExcel.write(response.getOutputStream()).registerWriteHandler(ExcelUtil.getStyleStrategy()).head(ExcelUtil.head(titles)).sheet("01").doWrite(ExcelUtil.dataList(tableData));
    }

    @ApiOperation("根据pid懒加载子节点")
    @PostMapping ("getChildrenData")
    public List<Map<String, Object>> getChildrenData(@RequestBody Map<String,Object> map){
        return tableService.getChildrenData(map);
    }
}

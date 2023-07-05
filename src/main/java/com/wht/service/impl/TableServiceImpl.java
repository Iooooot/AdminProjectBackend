package com.wht.service.impl;

import com.alibaba.excel.EasyExcel;
import com.wht.mapper.TableMapper;
import com.wht.service.TableService;
import com.wht.utils.ExcelUtil;
import com.wht.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 操作表格数据的service
 * @author wht
 * @date 2022/10/15 15:34
 */
@Service
public class TableServiceImpl implements TableService {

    @Autowired
    TableMapper tableMapper;
    // table的缓存空间名
    final String TABLECACHESPACE = "tableData";

    /**
     * 查询数据
     * @param map 前端传参
     * @return
     */
    @Override
    @Cacheable(value = TABLECACHESPACE)
    public List<Map<String, Object>> queryData(Map<String, Object> map) {
        // 获取参数
        // 查询字段
        String fields = (String) map.get("fields");
        // 表名
        String tableName = (String) map.get("tableName");
        // 查询字段
        List<String> queryFields = (List<String>) map.get("queryFields");
        // 查询的关键字
        String keyword = (String) map.get("keyword");
        // 分页大小
        Integer pageSize = (Integer) map.get("pageSize");
        // 当前页码
        Integer pageNum = (Integer)map.get("pageNum");
        // 是否树形
        Boolean isTree = (Boolean) map.get("isTree");
        if(!isTree){
            isTree = null;
        }
        int start = (pageNum - 1) * pageSize;
        List<Map<String, Object>> resultMap = tableMapper.query(fields, tableName, queryFields, keyword, start, pageSize,isTree);
        return resultMap;
    }

    /**
     * 插入数据
     * @param map 前端传的表名，以及插入数据
     * @return
     */
    @Override
    @CacheEvict(value = TABLECACHESPACE,allEntries = true)
    public Boolean addData(Map<String, Object> map) {
        // 获取表名
        String tableName = (String) map.get("tableName");
        // 获取插入字段以及相应值
        Map<String, Object> addData = (Map<String, Object>) map.get("addData");
        if(addData == null || addData.size() == 0){
            throw new RuntimeException("数据为空");
        }
        // 添加创建时间
        addData.put("create_time",new Date());
        addData.put("create_by", SecurityUtils.getCurrentUser().getUser().getUsername());
        return tableMapper.insert(tableName,addData);
    }

    /**
     * 根据id查询数据
     * @param map 表名，字段，id
     * @return
     */
    @Override
    public Map<String, Object> getOneTableData(Map<String, Object> map) {
        // 获取表名
        String tableName = (String) map.get("tableName");
        // 查询字段
        String fields = (String) map.get("fields");
        // 主键id
        Long id = Long.parseLong(map.get("id").toString());
        // 主键名
        String idName = (String) map.get("idName");
        return tableMapper.getOne(tableName,fields,id,idName);
    }

    /**
     * 根据id以及差异字段更新数据
     * @param map
     * @return
     */
    @Override
    @CacheEvict(value = TABLECACHESPACE,allEntries = true)
    public Boolean updateTableData(Map<String, Object> map) {
        // 获取表名
        String tableName = (String) map.get("tableName");
        // 获取差异字段
        Map<String, Object> diff = (Map<String, Object>) map.get("diff");
        // 主键id
        Long id = Long.parseLong(map.get("id").toString());
        // 主键名
        String idName = (String) map.get("idName");
        return tableMapper.update(tableName,diff,id,idName);
    }

    /**
     * 根据id删除一条数据
     * @param map
     * @return
     */
    @Override
    @CacheEvict(value = TABLECACHESPACE,allEntries = true)
    public Boolean delTableData(Map<String, Object> map) {
        // 获取表名
        String tableName = (String) map.get("tableName");
        // 主键id
        Long id = Long.parseLong(map.get("id").toString());
        // 主键名
        String idName = (String) map.get("idName");
        return tableMapper.del(tableName,id,idName);
    }

    @Override
    public void excelExport(Map<String, Object> map, HttpServletResponse response) throws IOException {
        // 文件名
        String fileName = (String) map.get("fileName");
        // 表数据
        List<Map<String, Object>> tableData = (List<Map<String, Object>>) map.get("tableData");
        // title
        Map<String, String> exportFields = (Map<String, String>) map.get("exportFields");
        List<String> titles = exportFields.values().stream().collect(Collectors.toList());
        // 大title
        String excelTitle = fileName + "数据表";
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");

        EasyExcel.write(response.getOutputStream()).registerWriteHandler(ExcelUtil.getStyleStrategy()).head(ExcelUtil.head(titles)).sheet("01").doWrite(ExcelUtil.dataList(tableData));
    }

    @Override
    public Integer getTableTotal(String tableName, Boolean isTree) {
        if(!isTree){
            isTree = null;
        }
        return tableMapper.getTableTotal(tableName,isTree);
    }

    @Override
    public List<Map<String, Object>> getChildrenData(Map<String, Object> map) {
        Integer pid = (Integer) map.get("pid");
        String tableName = (String) map.get("tableName");
        String fields = (String) map.get("fields");
        return tableMapper.getChildrenData(pid,tableName,fields);
    }

    @Override
    public List<Map<String, Object>> selectAllData(String fields, String tableName) {
        return tableMapper.selectAllData(fields,tableName);
    }


}

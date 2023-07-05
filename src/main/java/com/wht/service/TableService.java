package com.wht.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface TableService {
    /**
     * 查询数据
     * @param map 前端传参
     * @return
     */
    List<Map<String, Object>> queryData(Map<String, Object> map);

    /**
     * 插入数据
     * @param map 前端传的表名，以及插入数据
     * @return
     */
    Boolean addData(Map<String, Object> map);

    /**
     * 根据id查询数据
     * @param map 表名，字段，id
     * @return
     */
    Map<String, Object> getOneTableData(Map<String, Object> map);

    /**
     * 根据id以及差异字段更新数据
     * @param map
     * @return
     */
    Boolean updateTableData(Map<String, Object> map);

    /**
     * 根据id删除一条数据
     * @param map
     * @return
     */
    Boolean delTableData(Map<String, Object> map);

    /**
     * 导出excel
     * @param map
     * @param response
     */
    void excelExport(Map<String, Object> map, HttpServletResponse response) throws IOException;

    /**
     * 获取表格数据总数
     * @return
     */
    Integer getTableTotal(String tableName, Boolean isTree);

    /**
     * 根据pid查询子节点
     * @param map
     * @return
     */
    List<Map<String, Object>> getChildrenData(Map<String,Object> map);

    /**
     * 查询指定表的指定列数据
     * @param fields
     * @param tableName
     * @return
     */
    List<Map<String, Object>> selectAllData(String fields, String tableName);
}

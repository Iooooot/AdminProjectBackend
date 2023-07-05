package com.wht.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 表格数据mapper，用于封装动态sql
 * @author wht
 * @date 2022/10/15 15:48
 */
@Repository
public interface TableMapper {

    @SuppressWarnings("MybatisXMapperMethodInspection")
    /**
     * 查询表格数据
     * @param fields
     * @param tableName
     * @param queryFields
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    List<Map<String,Object>> query(@Param("fields") String fields, @Param("tableName") String tableName, @Param("queryFields") List<String> queryFields,
                                   @Param("keyword") String keyword, @Param("start") int start, @Param("end") int end,@Param("isTree") Boolean isTree);

    /**
     * 新增数据
     * @param tableName
     * @param addData
     * @return
     */
    Boolean insert(String tableName, Map<String, Object> addData);

    /**
     * 根据id查询单条数据
     * @param tableName
     * @param fields
     * @param id
     * @param idName
     * @return
     */
    @SuppressWarnings("MybatisXMapperMethodInspection")
    Map<String, Object> getOne(@Param("tableName") String tableName, @Param("fields") String fields, @Param("id") Long id,@Param("idName") String idName);

    /**
     * 根据id以及差异更新数据
     * @param tableName
     * @param diff
     * @param id
     * @param idName
     * @return
     */
    Boolean update(@Param("tableName") String tableName,@Param("diff") Map<String, Object> diff,@Param("id") Long id,@Param("idName") String idName);

    /**
     * 根据id删除一条数据
     * @param tableName
     * @param id
     * @param idName
     * @return
     */
    Boolean del(@Param("tableName") String tableName,@Param("id") Long id,@Param("idName") String idName);

    /**
     * 获取表格数据总数
     * @param tableName
     * @param isTree
     * @return
     */
    Integer getTableTotal(@Param("tableName") String tableName,@Param("isTree") Boolean isTree);

    /**
     * 根据pid查询子节点
     * @param pid
     * @param tableName
     * @param fields
     * @return
     */
    @SuppressWarnings("MybatisXMapperMethodInspection")
    List<Map<String, Object>> getChildrenData(@Param("pid") Integer pid, @Param("tableName") String tableName,@Param("fields") String fields);

    /**
     * 查询指定表的指定字段
     * @param fields
     * @param tableName
     * @return
     */
    @SuppressWarnings("MybatisXMapperMethodInspection")
    List<Map<String, Object>> selectAllData(@Param("fields")String fields, @Param("tableName")String tableName);
}

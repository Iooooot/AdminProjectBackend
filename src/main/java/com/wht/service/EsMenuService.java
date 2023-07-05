package com.wht.service;

import com.wht.entity.dto.EsMenuDto;

import java.io.IOException;
import java.util.List;

/**
 * es搜索菜单service
 * @author wht
 * @date 2022/10/25 18:47
 */
public interface EsMenuService {
    /**
     * 将数据库中菜单导入es
     * @return 返回数据条数
     */
    Integer importAll();

    /**
     * 根据id删除
     * @param id
     * @return
     */
    Boolean delete(Long id);
    /**
     * 根据id删除
     * @param id
     * @return
     */
    Boolean delete(List<Integer> id);

    /**
     * 根据id查询数据库并插入es
     * @param id
     * @return
     */
    Boolean insert(Integer id);

    /**
     * 分页简单搜素菜单
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<EsMenuDto> search(String keyword, Integer pageNum, Integer pageSize) throws IOException;

    /**
     * 首先进行icon以permission过滤，再根据关键字进行查找，最后排序
     * @param keyword
     * @param icon
     * @param permission
     * @param pageNum
     * @param pageSize
     * @param sort
     * @return
     */
    List<EsMenuDto> search(String keyword, String icon, String permission, Integer pageNum, Integer pageSize, Integer sort);

}

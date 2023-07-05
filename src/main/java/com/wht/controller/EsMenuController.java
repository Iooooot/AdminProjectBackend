package com.wht.controller;

import com.wht.annotation.SystemLog;
import com.wht.entity.dto.EsMenuDto;
import com.wht.service.EsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author wht
 * @date 2022/10/25 15:14
 */
@RestController
@Api(tags = "EsMenuController",description = "利用es搜索路由菜单模块")
@RequestMapping("/esMenu")
public class EsMenuController {

    @Autowired
    EsMenuService esMenuService;

    @ApiOperation(value = "导入所有数据库中菜单到ES")
    @PostMapping(value = "/importAll")
    public Integer importAllList() {
        return esMenuService.importAll();
    }


    @ApiOperation(value = "根据ides中的删除菜单")
    @GetMapping(value = "/delete")
    public Boolean delete(@RequestParam("id") Long id) {
        return esMenuService.delete(id);
    }

    @ApiOperation(value = "根据id批量删除菜单")
    @PostMapping(value = "/delete/batch")
    public Boolean delete(@RequestBody Map<String,Object> map) {
        List<Integer> ids = (List<Integer>)map.get("ids");
        return esMenuService.delete(ids);
    }

    @ApiOperation(value = "根据id插入菜单")
    @PostMapping(value = "/insert")
    @ResponseBody
    public Boolean insert(@RequestBody Map<String,Object> map) {
        Integer id = (Integer) map.get("id");
        return esMenuService.insert(id);
    }

    @ApiOperation(value = "简单搜索(根据标题、名称、组件、路径)")
    @SystemLog("ES简单搜索")
    @GetMapping(value = "/search/simple")
    public List<EsMenuDto> search(@RequestParam(required = false) String keyword,
                                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(required = false, defaultValue = "5") Integer pageSize) throws IOException {
        return esMenuService.search(keyword, pageNum, pageSize);
    }

    @ApiOperation(value = "综合搜索、筛选、排序")
    @SystemLog("ES综合搜索")
    @ApiImplicitParam(name = "sort", value = "排序字段:0->按菜单自带排序；1->按类型；2->按子菜单数",
            defaultValue = "0", allowableValues = "0,1,2", paramType = "query", dataType = "integer")
    @GetMapping(value = "/search")
    public List<EsMenuDto> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) String icon,
                                                      @RequestParam(required = false) String permission,
                                                      @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                      @RequestParam(required = false, defaultValue = "0") Integer sort) {
        return esMenuService.search(keyword, icon, permission, pageNum, pageSize, sort);
    }



}

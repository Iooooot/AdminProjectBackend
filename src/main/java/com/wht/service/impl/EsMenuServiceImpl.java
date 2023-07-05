package com.wht.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wht.entity.dto.EsMenuDto;
import com.wht.mapper.MenuMapper;
import com.wht.service.EsMenuService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wht
 * @date 2022/10/25 18:48
 */
@Service
@Slf4j
public class EsMenuServiceImpl implements EsMenuService {

    @Autowired
    ElasticsearchRestTemplate template;
    @Autowired
    MenuMapper menuMapper;

    final String[] sortFiled = {"menuSort", "type","subCount"};

    /**
     * 将数据库中菜单导入es
     * @return
     */
    @Override
    public Integer importAll() {
        // 创建索引
        Boolean flag = createIndex();
        if(flag){
            List<EsMenuDto> esMenuDtos = BeanUtil.copyToList(menuMapper.selectList(new LambdaQueryWrapper<>()), EsMenuDto.class);
            Iterator<EsMenuDto> iterator = template.save(esMenuDtos).iterator();
            int result = 0;
            while (iterator.hasNext()) {
                result++;
                iterator.next();
            }
            return result;
        }else{
            throw new RuntimeException("索引创建失败");
        }
    }

    @Override
    public Boolean delete(Long id) {
        String delete = template.delete(String.valueOf(id), EsMenuDto.class);
        log.info(delete);
        return true;
    }

    @Override
    public Boolean delete(List<Integer> ids) {
        String[] array = ids.stream().map(String::valueOf).toArray(String[]::new);
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.idsQuery().addIds(array)).build();
        ByQueryResponse delete = template.delete(query, EsMenuDto.class);
        log.info(delete.toString());
        return true;
    }

    @Override
    public Boolean insert(Integer id) {
        EsMenuDto esMenuDto = BeanUtil.toBean(menuMapper.selectById(id), EsMenuDto.class);
        template.save(esMenuDto);
        return true;
    }

    @Override
    public List<EsMenuDto> search(String keyword, Integer pageNum, Integer pageSize) throws IOException {
        //指定查询条件,根据标题、名称、组件、路径
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.termQuery("title", keyword))
                .should(QueryBuilders.termQuery("name", keyword))
                .should(QueryBuilders.termQuery("component", keyword))
                .should(QueryBuilders.termQuery("path", keyword));
        // 分页
        int start = (pageNum - 1) * pageSize;
        PageRequest pageable = PageRequest.of(start, pageSize);
        // 执行查询
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable).build();
        SearchHits<EsMenuDto> searchHits = template.search(query, EsMenuDto.class);
        log.info("DSL:{}", query.getQuery().toString());
        List<EsMenuDto> collect = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<EsMenuDto> search(String keyword, String icon, String permission, Integer pageNum, Integer pageSize, Integer sort) {

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //指定查询条件,根据标题、名称、组件、路径
        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("title", keyword),
                ScoreFunctionBuilders.weightFactorFunction(10)));
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("name", keyword),
                ScoreFunctionBuilders.weightFactorFunction(5)));
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("component", keyword),
                ScoreFunctionBuilders.weightFactorFunction(2)));
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("path", keyword),
                ScoreFunctionBuilders.weightFactorFunction(2)));
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
        filterFunctionBuilders.toArray(builders);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
        nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        // 设置过滤
        if (icon != null || permission != null) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (icon != null) {
                boolQueryBuilder.must(QueryBuilders.termQuery("icon", icon));
            }
            if (permission != null) {
                boolQueryBuilder.must(QueryBuilders.termQuery("permission", permission));
            }
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }
        // 排序
        FieldSortBuilder sortBuilder = new FieldSortBuilder(sortFiled[sort]).order(SortOrder.DESC);
        NativeSearchQuery query = nativeSearchQueryBuilder.withSort(sortBuilder).build();
        List<EsMenuDto> esMenuDtos = template.search(query, EsMenuDto.class).stream().map(SearchHit::getContent).collect(Collectors.toList());
        log.info("DSL:{}", query.getQuery().toString());
        return esMenuDtos;
    }

    public Boolean createIndex() {
        IndexOperations indexOperations = template.indexOps(EsMenuDto.class);
        if(indexOperations.exists()){
            return true;
        }
        //创建索引
        boolean a = indexOperations.create();
        if (a) {
            //生成映射
            Document document = indexOperations.createMapping();
            //推送映射
            return indexOperations.putMapping(document);

        } else {
            return false;
        }
    }

}

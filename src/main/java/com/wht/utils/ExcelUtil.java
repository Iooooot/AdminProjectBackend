package com.wht.utils;

import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wht
 * @date 2022/10/17 13:59
 */
public class ExcelUtil {
    //设置样式 去除默认表头样式及设置内容居中
    public static HorizontalCellStyleStrategy getStyleStrategy(){
        //头的策略(清除默认样式)
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillPatternType(FillPatternType.NO_FILL);
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)11);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        //设置 自动换行
        // contentWriteCellStyle.setWrapped(true);
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 11);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    public static List<List<String>> head(List<String> titles) {
        List<List<String>> list = ListUtils.newArrayList();
        for (String title : titles) {
            List<String> headTemp = ListUtils.newArrayList();
            headTemp.add(title);
            list.add(headTemp);
        }
        return list;
    }

    public static List<List<Object>> dataList(List<Map<String, Object>> datas) {
        List<List<Object>> list = ListUtils.newArrayList();
        for (Map<String, Object> map : datas) {
            List<Object> data = ListUtils.newArrayList();
            Collection<Object> values = map.values();
            for (Object value : values) {
                data.add(value);
            }
            list.add(data);
        }
        return list;
    }
}

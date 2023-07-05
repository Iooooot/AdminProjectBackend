package com.wht.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 订单表(Trade)表实体类
 *
 * @author makejava
 * @since 2022-10-23 19:55:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("trade")
public class Trade  {
    /**
     * 主键id
     */
    @TableId
    private Long id;
    /**
     * 商品名称
     */
    private String subject;
    /**
     * 商品订单号
     */
    private String outtradeno;
    /**
     * 第三方订单号
     */
    private String tradeno;
    /**
     * 订单总金额
     */
    private Double totalamount;
    /**
     * 订单状态，未支付为0反之1
     */
    private Integer state;
    /**
     * 创建时间
     */
    private String createtime;



}

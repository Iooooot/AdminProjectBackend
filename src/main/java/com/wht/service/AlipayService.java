package com.wht.service;

import com.wht.entity.Trade;

/**
 * 支付宝service
 * @author wht
 * @date 2022/10/24 9:36
 */
public interface AlipayService {
    /**
     * 支付宝网站支付
     * @param trade
     * @param isPC
     * @return
     */
    String alipayPagePay(Trade trade, Boolean isPC);
}

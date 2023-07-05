package com.wht.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.wht.entity.Trade;
import com.wht.service.AlipayService;
import com.wht.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wht
 * @date 2022/10/24 9:37
 */
@Service
public class AliPayServiceImpl implements AlipayService {

    @Autowired
    TradeService tradeService;

    /**
     * 支付宝网站支付
     * @param trade
     * @param isPC
     * @return
     */
    @Override
    public String alipayPagePay(Trade trade, Boolean isPC) {
        double money = trade.getTotalamount();
        double maxMoney = 5000;
        if(money <= 0 || money >= maxMoney){
            throw new RuntimeException("测试金额过大");
        }
        try {
            // 1.发起api调用
            if(isPC){
                AlipayTradePagePayResponse  res1 = Factory.Payment.Page()
                        .pay(trade.getSubject(), trade.getOuttradeno(), trade.getTotalamount().toString(),"http://localhost:8888/#/alipay/paying");
                // 2.处理异常
                // 判断是否支付成功
                if(ResponseChecker.success(res1)){
                    tradeService.save(trade);
                    return res1.getBody();
                }else{
                    throw new RuntimeException("支付失败！");
                }
            }else{
                AlipayTradeWapPayResponse res2 = Factory.Payment.Wap()
                        .pay(trade.getSubject(), trade.getOuttradeno(), trade.getTotalamount().toString(),"http://localhost:8888/#/sys-tools/aliPay","http://localhost:8888/#/alipay/paying");
                // 2.处理异常
                // 判断是否支付成功
                if(ResponseChecker.success(res2)){
                    return res2.getBody();
                }else{
                    throw new RuntimeException("支付失败！");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

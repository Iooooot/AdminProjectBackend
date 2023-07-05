package com.wht.controller;

import com.alipay.easysdk.factory.Factory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wht.annotation.SystemLog;
import com.wht.entity.Trade;
import com.wht.service.AlipayService;
import com.wht.service.TradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
@Api(tags = "工具：支付宝管理")
@Slf4j
public class AlipayController {

    @Autowired
    AlipayService alipayServiceImpl;

    @Autowired
    TradeService tradeService;

    /**
     * 支付宝网站支付
     * @param map
     * @param response
     * @return
     * @throws IOException
     */
    @ApiOperation("网页支付")
    @SystemLog("网页支付")
    @PostMapping("/toPay")
    @PreAuthorize("hasAuthority('alipay')")
    public String pay(@RequestBody Map<String,Object> map,HttpServletResponse response) throws IOException {
        String subject = (String) map.get("subject");
        Double totalAmount = (Double) map.get("totalAmount");
        Boolean isPC = (Boolean) map.get("isPc");
        //首先生成商品订单号
        Trade trade = new Trade();
        trade.setOuttradeno(getOrderCode());
        trade.setSubject(subject);
        trade.setTotalamount(totalAmount);
        // 生成创建时间
        trade.setCreatetime(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)).toString());
        // 设置支付状态为未支付
        trade.setState(0);
        //调用支付宝接口获取支付表单
        String result = alipayServiceImpl.alipayPagePay(trade,isPC);
        return result;
    }
    @ApiOperation("异步回调")
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========支付宝异步回调========");
        //获取支付宝请求的参数
        Map<String, String> params = getAllRequestParam(request);
        // 支付宝验签
        if (Factory.Payment.Common().verifyNotify(params)) {
            // 验签通过
            if("TRADE_SUCCESS".equals(params.get("trade_status"))){
                //交易成功
                String tradeNo = params.get("out_trade_no");
                if(tradeNo != null){
                    // 更新订单未已支付
                    Trade trade = new Trade();
                    trade.setState(1);
                    trade.setTradeno(params.get("trade_no"));
                    boolean flag = tradeService.update(trade, new LambdaUpdateWrapper<Trade>().eq(Trade::getOuttradeno, tradeNo));
                    if(!flag){
                        throw new RuntimeException("状态更新失败！");
                    }
                }else{
                    throw new RuntimeException("订单为空！");
                }
            }
        }
    }

    @ApiOperation("判断支付状态")
    @GetMapping("/judgePay")
    public Boolean payNotify(@RequestParam("tradeNo")String tradeNo) throws Exception {
        Trade trade = tradeService.getOne(new LambdaQueryWrapper<Trade>().eq(Trade::getOuttradeno, tradeNo));
        if(trade == null){
            throw new RuntimeException("订单未生成");
        }
        if(trade.getState() == 0){
            return false;
        }
        return true;
    }

    private Map<String, String> getAllRequestParam(HttpServletRequest request) {
        Map<String,String> res = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String temp = parameterNames.nextElement();
            String parameter = request.getParameter(temp);
            res.put(temp,parameter);
        }
        return res;
    }

    /**
     * 生成订单号
     * @return String
     */
    public String getOrderCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int a = (int)(Math.random() * 9000.0D) + 1000;
        System.out.println(a);
        Date date = new Date();
        String str = sdf.format(date);
        String[] split = str.split("-");
        String s = split[0] + split[1] + split[2];
        String[] split1 = s.split(" ");
        String s1 = split1[0] + split1[1];
        String[] split2 = s1.split(":");
        return split2[0] + split2[1] + split2[2] + a;
    }


}

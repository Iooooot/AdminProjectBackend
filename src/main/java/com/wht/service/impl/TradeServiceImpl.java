package com.wht.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wht.entity.Trade;
import com.wht.mapper.TradeMapper;
import com.wht.service.TradeService;
import org.springframework.stereotype.Service;

/**
 * 订单表(Trade)表服务实现类
 *
 * @author makejava
 * @since 2022-10-24 10:44:55
 */
@Service("tradeService")
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements TradeService {

}

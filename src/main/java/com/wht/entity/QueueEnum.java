package com.wht.entity;

import lombok.Getter;

/**
 * @author wht
 * @date 2022/10/20 10:14
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_EDITUSER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),
    /**
     * 延迟队列
     */
    QUEUE_Delay_CANCEL("delay.exchange", "delay.queue", "delay.key");

    /**
     * 交换名称
     */
    private final String exchange;
    /**
     * 队列名称
     */
    private final String queueName;
    /**
     * 路由键
     */
    private final String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.queueName = name;
        this.routeKey = routeKey;
    }
}

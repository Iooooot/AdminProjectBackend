package com.wht.config;


import com.wht.entity.RabbitReceiver;
import com.wht.entity.QueueEnum;
import com.wht.entity.RabbitSender;
import com.wht.utils.ProjectConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wht
 * @date 2022/10/20 10:02
 */
@Configuration
@Slf4j
public class DirectRabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        /*设置开启Mandatory才能触发回调函数，无论消息推送结果怎么样都强制调用回调函数*/
        rabbitTemplate.setMandatory(true);

        /*消息发送到Exchange的回调，无论成功与否*/
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback：" + "相关数据：" + correlationData);
            log.info("ConfirmCallback：" + "确认情况：" + ack);
            log.info("ConfirmCallback：" + "原因：" + cause);
        });

        /*消息从Exchange路由到Queue失败的回调*/
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("ReturnCallback：" + "消息：" + returnedMessage.getMessage());
            log.info("ReturnCallback：" + "回应码：" + returnedMessage.getReplyCode());
            log.info("ReturnCallback：" + "回应信息：" + returnedMessage.getReplyText());
            log.info("ReturnCallback：" + "交换机：" + returnedMessage.getExchange());
            log.info("ReturnCallback：" + "路由键：" + returnedMessage.getRoutingKey());
        });
        return rabbitTemplate;
    }

    /**
     * 自定义交换机 我们在这里定义的是一个延迟交换机
     */
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        //自定义交换机的类型
        args.put("x-delayed-type", "direct");
        return new CustomExchange(QueueEnum.QUEUE_Delay_CANCEL.getExchange(), "x-delayed-message", true, false, args);
    }

    /**
     * 延迟交换机对接的队列
     * @return
     */
    @Bean
    public Queue delayQueue() {
        return new Queue(QueueEnum.QUEUE_Delay_CANCEL.getQueueName(), true);
    }

    /**
     * 绑定延迟队列关系
     * @param delayQueue
     * @param delayedExchange
     * @return
     */
    @Bean
    public Binding bindingDelayedQueue(Queue delayQueue, CustomExchange delayedExchange) {
        return BindingBuilder.bind(delayQueue).to(delayedExchange).with(QueueEnum.QUEUE_Delay_CANCEL.getRouteKey()).noargs();
    }

    @Bean
    public DirectExchange direct() {
        return new DirectExchange(ProjectConst.DIRECTEXCCHANGE.getInfo(),true,false);
    }

    @Bean
    public Queue editUserQueue() {
        return new Queue("editUserQueue");
    }

    @Bean
    public Binding directBinding1(DirectExchange direct, Queue editUserQueue) {
        // 将editUserQueue队列与交换机绑定
        return BindingBuilder.bind(editUserQueue).to(direct).with("editUser");
    }

    @Bean
    public RabbitReceiver receiver() {
        return new RabbitReceiver();
    }


    @Bean
    public RabbitSender directSender() {
        return new RabbitSender();
    }

}

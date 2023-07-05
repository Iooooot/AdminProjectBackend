package com.wht.entity;

import com.rabbitmq.client.Channel;
import com.wht.service.MeetingService;
import com.wht.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author wht
 * @date 2022/10/20 10:31
 */
@Component
@Slf4j
public class RabbitReceiver {


    @Autowired
    UserService userService;

    @Autowired
    MeetingService meetingService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "editUserQueue")
    public void handlEeditUser(User user, Channel channel, Message message)throws IOException{

        try {
            userService.updateById(user);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("消息即将再次返回队列处理...");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    @RabbitListener(queues = "delay.queue")
    public void handleMeeting(Map<String,Object> map, Channel channel, Message message)throws IOException {
        try {
            log.info("进行邮件发送...");
            meetingService.sendMeetingEamil(map);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("消息即将再次返回队列处理...");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

}

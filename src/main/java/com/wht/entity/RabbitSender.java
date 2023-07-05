package com.wht.entity;

import com.wht.utils.ProjectConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wht
 * @date 2022/10/20 10:19
 */
@Component
public class RabbitSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 将需要更新的用户信息存入队列
     * @param user
     */
    public void sendUpdateUser(User user) {
        rabbitTemplate.convertAndSend(ProjectConst.DIRECTEXCCHANGE.getInfo(), "editUser", user);
    }

    /**
     * 预约会议
     * @param map
     * @param millisecond 延迟提醒时间
     */
    public void sendMeeting(Map<String,Object> map,long millisecond) {
        rabbitTemplate.convertAndSend(
                QueueEnum.QUEUE_Delay_CANCEL.getExchange(),
                QueueEnum.QUEUE_Delay_CANCEL.getRouteKey(),
                map,
                correlationData ->{
                    correlationData.getMessageProperties().setDelay((int) millisecond);
                    return correlationData;
                }
        );
    }
}

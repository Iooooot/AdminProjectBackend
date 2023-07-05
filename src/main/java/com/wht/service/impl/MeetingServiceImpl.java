package com.wht.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.wht.entity.RabbitSender;
import com.wht.service.MeetingService;
import com.wht.utils.EmailUtil;
import com.wht.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author wht
 * @date 2022/10/21 10:26
 */
@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public Boolean appointMeeting(Map<String, Object> map) {
        // 存入用户邮箱
        map.put("email",SecurityUtils.getCurrentUser().getUser().getEmail());
        // 获取日期
        String date = (String) map.get("date");
        // 获取时间
        String time = (String) map.get("time");
        date = date.substring(0, 10);
        time = time.substring(11, 19);
        LocalDateTime dateTime = LocalDateTime.parse(date +" "+ time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusHours(8);
        map.remove("date");
        map.remove("time");
        // 计算延迟时间（微秒）
        long between = LocalDateTimeUtil.between(LocalDateTime.now(), dateTime).getSeconds();
        if (between + 60*1000 < 0){
            throw new RuntimeException("请选择正确的时间");
        }
        long delay = between*1000 - 60*1000;
        rabbitSender.sendMeeting(map,delay);
        return true;
    }

    @Override
    public void sendMeetingEamil(Map<String, Object> map) {
        // 获取会议号
        String meetNum = (String) map.get("meetNum");
        // 获取会议主题
        String title = (String) map.get("title");
        //获取主持人
        String host = (String) map.get("host");
        // 获取邮箱
        String email = (String) map.get("email");
        emailUtil.sendMeetingEamil(title,host,meetNum,email);
    }

}

package com.wht.service;

import java.util.Map;

/**
 * 会议预约的service
 * @author wht
 * @date 2022/10/21 10:14
 */

public interface MeetingService {
    /**
     * 预约会议
     * @param map
     * @return
     */
    Boolean appointMeeting(Map<String, Object> map);

    /**
     * 发送会议提醒邮件
     * @param map
     */
    void sendMeetingEamil(Map<String, Object> map);
}

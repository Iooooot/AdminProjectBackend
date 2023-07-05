package com.wht.controller;

import com.wht.annotation.SystemLog;
import com.wht.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wht
 * @date 2022/10/21 10:12
 */
@Api(tags = "MeetingController", description = "会议预约模块")
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @PostMapping(value = "/appointMeeting")
    @ApiOperation("预约会议")
    @SystemLog("预约会议")
    @PreAuthorize("hasAuthority('meeting')")
    public Boolean appointMeeting(@RequestBody Map<String,Object> map){
        return meetingService.appointMeeting(map);
    }
}

package com.wht.utils;


import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * @author wht
 * @date 2022/10/7 21:39
 */
@Component
public class EmailUtil {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    VerifyCodeUtil verifyCodeUtil;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    RedisUtil redisUtil;

    public String sendEmailVerificationCode(String toAddress){
        // 获取验证码
        String code = verifyCodeUtil.generateVerificationCode(4);
        // 创建邮件模板正文
        Context context = new Context();
        context.setVariable("emailCode", Arrays.asList(code.split("")));
        // 将模块引擎内容解析成html字符串
        String emailContent = templateEngine.process("emailCodeTemplates", context);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom("1369281736@qq.com");
            helper.setTo(toAddress);
            helper.setSubject("MyAdmin：邮箱验证码");
            helper.setText(emailContent,true);
            mailSender.send(message);
            // 将code存redis
            String uuid = IdUtil.simpleUUID();
            redisUtil.set(ProjectConst.EMAIL_CODE.getInfo() + ":" + uuid, code,60);
            return uuid;
        }catch (MessagingException e) {
            throw new RuntimeException("邮箱验证码发送错误！");
        }
    }

    public void sendMeetingEamil(String meetingTitle,String meetingHost,String meetingNum,String toAddress){
        // 创建邮件模板正文
        Context context = new Context();
        context.setVariable("meetingNum", Arrays.asList(meetingNum.split("")));
        context.setVariable("meetingTitle", meetingTitle);
        context.setVariable("meetingHost", meetingHost);
        // 将模块引擎内容解析成html字符串
        String emailContent = templateEngine.process("meetingEmailTemplates", context);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom("1369281736@qq.com");
            helper.setTo(toAddress);
            helper.setSubject("MyAdmin：会议预约");
            helper.setText(emailContent,true);
            mailSender.send(message);
        }catch (MessagingException e) {
            throw new RuntimeException("会议预约邮件发送错误！");
        }
    }


}

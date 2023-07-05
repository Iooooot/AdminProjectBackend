package com.wht.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author wht
 * @date 2022/10/30 10:43
 */
@Slf4j
public class IpAddressUtils {

    private static final String UNKNOWN = "unknown";

    /**
     * 根据ip获取地址
     * @param ip
     * @return
     */
    public static String getAddress(String ip) {
        String url = "http://whois.pconline.com.cn/ip.jsp?ip=" + ip;
        String str = HttpUtil.get(url);
        if(!StrUtil.hasBlank(str)){
            str = str.replace(" ","");
            str = str.replace("\n","");
            return str;
        }
        return null;
    }

    /**
     * 根据请求获取浏览器信息
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        return userAgent.getBrowser().toString();
    }

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }
}

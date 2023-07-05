package com.wht.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@Slf4j
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    private String appId;
    private String privateKey;
    private String publicKey;
    private String notifyUrl;


    @PostConstruct
    public void init() {
        // 设置参数（全局只需设置一次）
        Config options = getOptions();
        options.appId = this.appId;
        options.merchantPrivateKey = this.privateKey;
        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        options.alipayPublicKey = this.publicKey;
        options.notifyUrl = this.notifyUrl;
        Factory.setOptions(options);
        log.info("=======支付宝SDK初始化成功=======");
    }

    private Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";
        return config;
    }
}

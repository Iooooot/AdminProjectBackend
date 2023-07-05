package com.wht.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wht
 * @date 2022/10/22 18:24
 */
@Component
@Data
public class COSProperties {

    @Value("${cos.secretId}")
    private String secretId;
    @Value("${cos.secretKey}")
    private String secretKey;
    @Value("${cos.regionName}")
    private String regionName;
    @Value("${cos.bucketName}")
    private String bucketName;

}

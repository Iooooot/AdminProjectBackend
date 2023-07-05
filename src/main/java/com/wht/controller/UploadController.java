package com.wht.controller;

import com.wht.annotation.SystemLog;
import com.wht.utils.UploadUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wht
 * @date 2022/10/22 12:57
 */
@Api(tags = "UploadController", description = "上传模块")
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    UploadUtil uploadUtil;

    @PostMapping("pictures")
    @SystemLog("上传照片")
    public String uploadImage(@RequestParam("file") MultipartFile file){
        return  uploadUtil.uploadImage(file);
    }
}

package com.msp.oss.controller;

import com.msp.eduOrder.commonUtils.MResult;
import com.msp.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss/fileOss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public MResult uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return MResult.ok().data("url", url);
    }
}

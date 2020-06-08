package com.msp.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.msp.oss.service.OssService;
import com.msp.oss.utils.BaseProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private BaseProperties bp;

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String bucketName = bp.getBucketName();
        String endPoint = bp.getEndPoint();
        String accessKeyId = bp.getKeyId();
        String accessKeySecret = bp.getKeySecret();

  /*      // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endPoint = BaseProperties.END_POIND;
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = BaseProperties.ACCESS_KEY_ID;
        String accessKeySecret = BaseProperties.ACCESS_KEY_SECRET;
        String bucketName = BaseProperties.BUCKET_NAME;*/


        // 上传网络流。
        InputStream inputStream = null;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            inputStream = file.getInputStream();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            String datePath = new DateTime().toString("yyyy/MM/dd");

            String fileName = datePath + "/" + uuid + file.getOriginalFilename();

            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            // https://eduonline2.oss-cn-hangzhou.aliyuncs.com/QQ%E5%9B%BE%E7%89%8720200428191809.jpg
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}

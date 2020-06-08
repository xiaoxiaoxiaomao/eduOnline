package com.msp.vod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.msp.serviceBase.exceptionHandler.EduOnlineException;
import com.msp.vod.utils.BaseProperties;
import com.msp.vod.utils.InitClient;
import com.msp.vod.service.VodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Autowired
    private BaseProperties bp;

    /**
     * 上传视频
     *
     * @param file
     * @return
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        InputStream inputStream;
        String videoId;
        try {
            String keyid = bp.getKeyid();
            String keysecret = bp.getKeysecret();

            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));

            String accessKeyId = keyid;
            String accessKeySecret = keysecret;

            inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }

            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * 删除视频
     *
     * @param id
     */
    @Override
    public void deleteVideoById(String id) {

        try {
            DefaultAcsClient client = InitClient.initVodClient(bp.getKeyid(), bp.getKeysecret());
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(id);
            client.getAcsResponse(request);

        } catch (ClientException e) {
            e.printStackTrace();
            throw new EduOnlineException(20001, "删除视频失败");
        }
    }

    /**
     * 批量删除视频
     *
     * @param ids
     */
    @Override
    public void deleteMoreVideos(List<String> ids) {

        try {
            DefaultAcsClient client = InitClient.initVodClient(bp.getKeyid(), bp.getKeysecret());
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            String id = StringUtils.join(ids.toArray(), ",");
            request.setVideoIds(id);
            client.getAcsResponse(request);

        } catch (ClientException e) {
            e.printStackTrace();
            throw new EduOnlineException(20001, "删除视频失败");
        }
    }

    @Override
    public String getPlayAuth(String id) {

        try {
            DefaultAcsClient client = InitClient.initVodClient(bp.getKeyid(), bp.getKeysecret());
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return playAuth;
        } catch (ClientException e) {
            e.printStackTrace();
            throw new EduOnlineException(20001, "获取凭证失败");
        }
    }

}

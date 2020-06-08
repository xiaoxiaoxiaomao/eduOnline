package com.msp.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile multipartFile);

    void deleteVideoById(String id);

    void deleteMoreVideos(List<String> ids);

    String getPlayAuth(String id);
}

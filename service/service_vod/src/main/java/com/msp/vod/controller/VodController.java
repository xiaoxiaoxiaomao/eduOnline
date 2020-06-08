package com.msp.vod.controller;

import com.msp.eduOrder.commonUtils.MResult;
import com.msp.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @PostMapping("uploadVideo")
    public MResult uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return MResult.ok().data("videoId",videoId);
    }

    @DeleteMapping("deleteVideo/{id}")
    public MResult deleteVideoById(@PathVariable String id){

        vodService.deleteVideoById(id);
        return MResult.ok();
    }

    @DeleteMapping("deleteVideos")
    public MResult deleteMoreVideos(@RequestParam List<String> ids){
        vodService.deleteMoreVideos(ids);
        return MResult.ok();
    }

    /**
     * 获取视频凭证
     * @param id
     * @return
     */
    @GetMapping("getPlayAuth/{id}")
    public MResult getPlayAuth(@PathVariable String id){
       String auth = vodService.getPlayAuth(id);
       return MResult.ok().data("auth",auth);
    }
}

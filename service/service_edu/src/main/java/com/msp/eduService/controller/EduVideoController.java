package com.msp.eduService.controller;


import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduService.client.VodClient;
import com.msp.eduService.entity.EduVideo;
import com.msp.eduService.service.EduVideoService;
import com.msp.serviceBase.exceptionHandler.EduOnlineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author msp
 * @since 2020-05-22
 */
@RestController
@RequestMapping("/eduService/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    @PostMapping("addVideo")
    public MResult addVideo(@RequestBody EduVideo eduVideo) {
        boolean flag = eduVideoService.save(eduVideo);
        if (flag){
            return MResult.ok();
        }
        return MResult.error();

    }

    //删除小节，，视频？
    @DeleteMapping("{id}")
    public MResult deleteVideo(@PathVariable String id) {
        //视频id
        EduVideo video = eduVideoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        System.out.println(videoSourceId);
        if (!StringUtils.isEmpty(videoSourceId)){
            MResult mResult = vodClient.deleteVideoById(videoSourceId);
            if (mResult.getCode() == 20001){
                throw new EduOnlineException(20001,"熔断");
            }
        }

        eduVideoService.removeById(id);
        return MResult.ok();
    }

    @GetMapping("getVideo/{id}")
    public MResult getVideoById(@PathVariable String id) {
        EduVideo video = eduVideoService.getById(id);
        return MResult.ok().data("video", video);
    }

    @PostMapping("updateVideo")
    public MResult updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return MResult.ok();
    }


}


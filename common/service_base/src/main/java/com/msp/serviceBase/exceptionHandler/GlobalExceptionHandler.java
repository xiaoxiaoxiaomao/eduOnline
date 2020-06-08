package com.msp.serviceBase.exceptionHandler;

import com.msp.eduOrder.commonUtils.MResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public MResult error(Exception e){
        e.printStackTrace();
        return MResult.error().message("执行了全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public MResult error(ArithmeticException e){
        e.printStackTrace();
        return MResult.error().message("执行了ArithmeticException异常处理");
    }

    @ExceptionHandler(EduOnlineException.class)
    @ResponseBody
    public MResult error(EduOnlineException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return MResult.error().code(e.getCode()).message(e.getMsg());
    }
}

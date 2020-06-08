package com.msp.serviceBase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EduOnlineException extends RuntimeException {

    private Integer code;
    private String msg;


}

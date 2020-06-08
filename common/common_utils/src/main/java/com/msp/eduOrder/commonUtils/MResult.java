package com.msp.eduOrder.commonUtils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data

public class MResult {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data = new HashMap<String, Object>();

   private MResult(){}

   public static MResult ok(){
       MResult mResult = new MResult();
       mResult.setCode(ResultCode.SUCCESS);
       mResult.setSuccess(true);
       mResult.setMessage("成功");
       return mResult;
   }
    public static MResult error(){
        MResult mResult = new MResult();
        mResult.setCode(ResultCode.Failure);
        mResult.setSuccess(false);
        mResult.setMessage("失败");
        return mResult;
    }

    public MResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public MResult message(String message){
        this.setMessage(message);
        return this;
    }

    public MResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public MResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public MResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}

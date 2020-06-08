package com.msp.msm.service;

import java.util.HashMap;

public interface MsmService {
    boolean sendMsg(HashMap<String, Object> param, String phone);
}

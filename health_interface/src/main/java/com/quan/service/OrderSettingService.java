package com.quan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.quan.pojo.OrderSetting;

import java.util.List;


public interface OrderSettingService {
    public void add(List<OrderSetting> list);
}

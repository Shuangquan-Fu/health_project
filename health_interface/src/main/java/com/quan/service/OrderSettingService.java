package com.quan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.quan.pojo.OrderSetting;

import java.util.List;
import java.util.Map;


public interface OrderSettingService {
    public void add(List<OrderSetting> list);
    public List<Map> getOrderSettingByMonth(String date);
    public void editNumberByDate(String orderDate, int number);
}

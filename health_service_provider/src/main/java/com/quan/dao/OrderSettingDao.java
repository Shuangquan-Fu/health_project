package com.quan.dao;

import com.quan.pojo.OrderSetting;

import java.util.Date;

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date orderDate);
}

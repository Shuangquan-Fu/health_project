package com.quan.dao;

import com.quan.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date orderDate);
    public List<OrderSetting> getOrderSettingByMonth(Map date);
    public OrderSetting findOrderSettingByDate(Date date);
    public void editReservationsByOrderDate(OrderSetting orderSetting);
}

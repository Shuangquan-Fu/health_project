package com.quan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.quan.dao.OrderSettingDao;
import com.quan.pojo.Order;
import com.quan.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService{
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void editNumberByDate(String orderDate, int number) {
        Date date = new Date(orderDate);
        OrderSetting orderSetting = new OrderSetting(date,number);
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有进行预约设置，进行添加操作
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String dateBegin = date + "-1";//2019-3-1
        String dateEnd = date + "-31";//2019-3-31
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> data = new ArrayList<>();
        for(OrderSetting orderSetting : orderSettings){
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
            orderSettingMap.put("number",orderSetting.getNumber());
            orderSettingMap.put("reservations",orderSetting.getReservations());
            data.add(orderSettingMap);
        }
        return data;
    }

    @Override
    public void add(List<OrderSetting> list) {
        //logic: add or edit for corresponding date
        if(list != null && list.size() > 0){
            for(OrderSetting order : list){
                // find the number of order at that day

                long count = orderSettingDao.findCountByOrderDate(order.getOrderDate());
                if(count > 0){
                    //update order
                    orderSettingDao.editNumberByOrderDate(order);
                } else {
                    //set new order
                    orderSettingDao.add(order);
                }
            }
        }
    }
}

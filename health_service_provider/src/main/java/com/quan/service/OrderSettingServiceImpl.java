package com.quan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.quan.dao.OrderSettingDao;
import com.quan.pojo.Order;
import com.quan.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService{
    @Autowired
    private OrderSettingDao orderSettingDao;
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

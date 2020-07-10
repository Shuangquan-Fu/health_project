package com.quan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.quan.constant.MessageConstant;
import com.quan.dao.MemberDao;
import com.quan.dao.OrderDao;
import com.quan.dao.OrderSettingDao;
import com.quan.entity.Result;
import com.quan.pojo.Member;
import com.quan.pojo.Order;
import com.quan.pojo.OrderSetting;
import com.quan.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }

    @Override
    public Result order(Map map) throws Exception {
        //1.whether order has been confirmed before
        //2. order number is full or not
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        //get orderStting
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByDate(date);
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if(reservations >= number){
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if(member != null){
            Integer memberId = member.getId();
            int setmealId = Integer.parseInt((String)map.get("setmealId"));
            // use dynamic searching for different condition
            //here can also use map to search
            Order order = new Order(memberId,date,null,null,setmealId);
            List<Order> list = orderDao.findByCondition(order);
            if(list != null && list.size() > 0){
                //已经完成了预约，不能重复预约
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        //3. check whether order twice
        //4.if it is not a member then create member and make appointment
        if(member == null){
            //当前用户不是会员，需要添加到会员表
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }
        Order order = new Order(member.getId(),
                date,
                (String)map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);
        //5.if order is good, then update reservation and update order list
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }
}

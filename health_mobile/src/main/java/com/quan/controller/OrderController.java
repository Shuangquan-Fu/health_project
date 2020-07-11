package com.quan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.quan.constant.MessageConstant;
import com.quan.constant.RedisMessageConstant;
import com.quan.entity.Result;
import com.quan.pojo.Order;
import com.quan.service.OrderService;
import com.quan.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

//make appointment
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("findById")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById(id);
            return  new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map map){
        //check the phone use redis
        String telephone = (String) map.get("telephone");
        Jedis jedis = jedisPool.getResource();
        String codeInRedis = jedis.get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);
        jedis.close();
        String validateCode = (String) map.get("validateCode");
        if(codeInRedis == null || !codeInRedis.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //make appointment
        Result result = null;
        try{
            map.put("orderType",Order.ORDERTYPE_WEIXIN);
            //use service
            result = orderService.order(map);
        } catch(Exception e){
            e.printStackTrace();
            return result;
        }
        if(result.isFlag()){
            //sent message to user for confirmation
            String orderDate = (String) map.get("orderDate");
            try{
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
}

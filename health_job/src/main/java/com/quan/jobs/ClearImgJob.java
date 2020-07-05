package com.quan.jobs;

import com.quan.constant.RedisConstant;
import com.quan.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void clearImg(){
        Jedis jedis = jedisPool.getResource();
        Set<String> set = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set != null && set.size() > 0){
            for (String picName : set) {
                //删除七牛云服务器上的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                System.out.println("delete image from qiniu");
                //从Redis集合中删除图片名称
                jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("redis deleting");
            }
        }
        jedis.close();
    }
}

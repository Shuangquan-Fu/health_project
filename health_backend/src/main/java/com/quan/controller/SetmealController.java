package com.quan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.filter.EchoFilter;
import com.quan.constant.MessageConstant;
import com.quan.constant.RedisConstant;
import com.quan.entity.PageResult;
import com.quan.entity.QueryPageBean;
import com.quan.entity.Result;
import com.quan.pojo.Setmeal;
import com.quan.service.SetmealService;
import com.quan.utils.QiniuUtils;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {


    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        Jedis jedis = jedisPool.getResource();
        try{
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(lastIndexOf);
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
            jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }finally {
            jedis.close();
        }
    }
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try{
            setmealService.add(setmeal,checkgroupIds);
        } catch (Exception e){
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.pageQuery(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findById(id);
        if(setmeal == null){
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
    @RequestMapping("findCheckGroupIdsByCheckGroupId")
    public Result findCheckGroupIdsByCheckGroupId(Integer id){
        try{
            List<Integer> checkGroups = setmealService.findCheckGroup(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);
        } catch (Exception e){
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try{
            setmealService.edit(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e){
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("delete")
    public Result delete(Integer id){
        try {
            setmealService.delete(id);
        }catch (Exception e){
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}

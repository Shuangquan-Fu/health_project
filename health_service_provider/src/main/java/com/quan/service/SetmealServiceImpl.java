package com.quan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.quan.constant.RedisConstant;
import com.quan.dao.SetmealDao;
import com.quan.entity.PageResult;
import com.quan.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SetmealServiceImpl implements SetmealService{
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if(checkgroupIds != null && checkgroupIds.length > 0){
            setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }
        savePic2Redis(setmeal.getImg());
    }

    private void savePic2Redis(String pic){
        Jedis jedis = jedisPool.getResource();
        jedis.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
        jedis.close();
    }

    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            Map<String,Integer> map = new HashMap<>();
            map.put("setmealId",id);
            map.put("checkgroupId",checkgroupId);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Integer> findCheckGroup(Integer id) {
        List<Integer> checkGroupIds = setmealDao.findCheckGroupIds(id);
        return checkGroupIds;
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] ids) {
        //edit
        //clear all relationship

        setmealDao.deleteAssosiation(setmeal.getId());
        setSetmealAndCheckGroup(setmeal.getId(),ids);
        setmealDao.edit(setmeal);
        //set new realtionship
    }

    @Override
    public void delete(Integer id) {
        setmealDao.deleteAssosiation(id);
        setmealDao.delete(id);
    }
}

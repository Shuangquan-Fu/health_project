package com.quan.service;

import com.quan.entity.PageResult;
import com.quan.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
    public Setmeal findById(Integer id);
    public List<Integer> findCheckGroup(Integer id);
    public void edit(Setmeal setmeal, Integer[] ids);
    public void delete(Integer id);
}

package com.quan.dao;

import com.github.pagehelper.Page;
import com.quan.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    public void add(Setmeal setmeal);
    public void setSetmealAndCheckGroup(Map<String, Integer> map);
    public Page<Setmeal> selectByCondition(String queryString);
    public Setmeal findById(Integer id);
    public List<Integer> findCheckGroupIds(Integer id);
    public void edit(Setmeal setmeal);
    public void deleteAssosiation(Integer id);
    public void delete(Integer id);
    public long findCountSeal(Integer id);
    public List<Setmeal> findAll();
    public List<Map<String,Object>> findSetmealCount();
}


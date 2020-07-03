package com.quan.service;

import com.quan.entity.PageResult;
import com.quan.entity.QueryPageBean;
import com.quan.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
    public void deleteById(Integer id);
    public CheckItem findById(Integer id);
    public void edit(CheckItem checkItem);
    public List<CheckItem> findAll();
}

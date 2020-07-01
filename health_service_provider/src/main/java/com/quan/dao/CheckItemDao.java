package com.quan.dao;

import com.github.pagehelper.Page;
import com.quan.entity.PageResult;
import com.quan.entity.QueryPageBean;
import com.quan.pojo.CheckItem;

public interface CheckItemDao {

    public void add(CheckItem checkItem);
    public Page<CheckItem> selectByCondition(String queryString);

}

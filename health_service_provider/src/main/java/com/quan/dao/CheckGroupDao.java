package com.quan.dao;

import com.quan.pojo.CheckGroup;

import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);
    void setCheckGroupAndCheckItem(Map map);
}

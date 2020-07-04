package com.quan.dao;

import com.github.pagehelper.Page;
import com.quan.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);
    void setCheckGroupAndCheckItem(Map map);
    Page<CheckGroup> selectByCondition(String queryString);
    CheckGroup findById(Integer id);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    void deleteAssociation(Integer id);
    void edit(CheckGroup checkGroup);
}

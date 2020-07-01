package com.quan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.quan.dao.CheckItemDao;
import com.quan.entity.PageResult;
import com.quan.entity.QueryPageBean;
import com.quan.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService{
    //add new checkitem
    @Autowired
    private CheckItemDao checkItemDao;

    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}

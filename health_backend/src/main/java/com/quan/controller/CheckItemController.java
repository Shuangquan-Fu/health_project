package com.quan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.quan.constant.MessageConstant;
import com.quan.entity.PageResult;
import com.quan.entity.QueryPageBean;
import com.quan.entity.Result;
import com.quan.pojo.CheckItem;
import com.quan.service.CheckItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        } catch (Exception e){
            System.out.println(e);
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }
}

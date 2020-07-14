package com.quan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.quan.dao.MemberDao;
import com.quan.dao.OrderDao;
import com.quan.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReportServiceImpl implements ReportService{
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Override
    public Map<String, Object> getBusinessReport() throws Exception {
        //get date
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //get monday time
        String thisweekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //get month time
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //get member number for day
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        //get total member
        Integer totalMember = memberDao.findMemberTotalCount();
        //get member for week
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisweekMonday);
        //get member for month
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);
        //get reservation for today
        Integer todayOrderNumber = orderDao.findOrederCountByDate(today);
        //for month
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisweekMonday);
        //for comming today
        Integer thisMonthOrderNumber =
                orderDao.findOrderCountAfterDate(firstDay4ThisMonth);
        //for comming this week
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        //for comming this month
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisweekMonday);
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);
        // get top 5 meal
        List<Map> hotSetmeal = orderDao.findHotSetmeal();
        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);
        return result;
    }
}

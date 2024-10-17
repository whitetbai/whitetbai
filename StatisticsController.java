package com.museum.ticketsystem.controller;

import com.museum.ticketsystem.model.Statistic;
import com.museum.ticketsystem.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticService statisticService;

    // 按日期查询售票统计
    @GetMapping("/daily")
    public Statistic getDailyStatistics(@RequestParam Date date) {
        return statisticService.getStatisticsByDate(date);
    }

    // 按月、年查询售票数据
    @GetMapping("/monthly")
    public List<Statistic> getMonthlyStatistics(@RequestParam int year, @RequestParam int month) {
        return statisticService.getMonthlyStatistics(year, month);
    }

    @GetMapping("/yearly")
    public List<Statistic> getYearlyStatistics(@RequestParam int year) {
        return statisticService.getYearlyStatistics(year);
    }
}

package com.museum.ticketsystem.service;

import com.museum.ticketsystem.model.Statistic;
import com.museum.ticketsystem.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    // 按日期查询统计
    public Statistic getStatisticsByDate(Date date) {
        return statisticRepository.findByDate(date);
    }

    // 按月查询统计
    public List<Statistic> getMonthlyStatistics(int year, int month) {
        // 根据年和月查询统计数据
        // 假设已经实现了 findByYearAndMonth 方法
        return statisticRepository.findByYearAndMonth(year, month);
    }

    // 按年查询统计
    public List<Statistic> getYearlyStatistics(int year) {
        return statisticRepository.findByYear(year);
    }
}

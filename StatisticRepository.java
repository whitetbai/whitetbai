package com.museum.ticketsystem.repository;

import com.museum.ticketsystem.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    // 添加此方法
    Statistic findByDate(Date date);
    @Query("SELECT s FROM Statistic s WHERE YEAR(s.date) = ?1 AND MONTH(s.date) = ?2")
    List<Statistic> findByYearAndMonth(int year, int month);
    @Query("SELECT s FROM Statistic s WHERE YEAR(s.date) = ?1")
    List<Statistic> findByYear(int year);
}

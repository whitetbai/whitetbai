package com.museum.ticketsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;  // 导入 java.util.Date

@Entity
@Data
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statId;

    private Date date;  // 使用 java.util.Date
    private Integer dailySales;
    private Integer monthlySales;
    private Integer yearlySales;
    private Double totalRevenue;
}

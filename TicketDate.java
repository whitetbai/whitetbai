package com.museum.ticketsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class TicketDate {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketDateId;

    @ManyToOne
    @JoinColumn(name = "ticketId", nullable = false)  // 外键关联到 Ticket 表
    private Ticket ticket;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    private Integer totalQuantity; // 总票数
    private Integer reserveQuantity; // 已预定票数


    // 计算剩余票数的虚拟字段
    @Transient
    public Integer getAvailableQuantity() {
        return totalQuantity - reserveQuantity; // 计算可用票数
    }
    public TicketDate() {
        this.totalQuantity = 100;  // 默认总票数为100
        this.reserveQuantity = 0;   // 默认已预定票数为0
    }

    // 不再需要 setAvailableQuantity 方法
}

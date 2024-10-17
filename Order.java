package com.museum.ticketsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Orders")  // 指定数据库中的表名
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)  // 外键关联到 User 表，使用 user_id 作为列名
    private User user;  // 使用 User 类型

    private double totalPrice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    // Getter 和 Setter 方法
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;  // 返回 User 类型
    }

    public void setUser(User user) {  // 接受 User 类型
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

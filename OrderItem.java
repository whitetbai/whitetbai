package com.museum.ticketsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orderitem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)  // 与 Order 关联
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ticketId", nullable = false)  // 与 Ticket 关联
    private Ticket ticket;

    private int quantity;  // 订票数量

    private String visitorName;  // 游客姓名

    private String visitorIDNumber;  // 游客身份证号

    // Getter 和 Setter 方法

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {  // 设置关联的订单
        this.order = order;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {  // 设置关联的门票
        this.ticket = ticket;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorIDNumber() {
        return visitorIDNumber;
    }

    public void setVisitorIDNumber(String visitorIDNumber) {
        this.visitorIDNumber = visitorIDNumber;
    }
}

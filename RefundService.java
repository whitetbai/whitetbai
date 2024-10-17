package com.museum.ticketsystem.service;

import com.museum.ticketsystem.model.Order;
import com.museum.ticketsystem.model.Refund;
import com.museum.ticketsystem.repository.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

@Service
public class RefundService {

    @Autowired
    private RefundRepository refundRepository;

    // 获取所有退款记录
    public List<Refund> getAllRefunds() {
        return refundRepository.findAll();
    }

    // 创建退款
    public void createRefund(Order order, int quantity) {
        Refund refund = new Refund();
        refund.setOrderId(order.getOrderId());
        refund.setQuantity(quantity);
        refund.setRefundDate(new Date());  // 设置退款日期
        refundRepository.save(refund);
    }
}

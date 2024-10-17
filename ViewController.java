package com.museum.ticketsystem.controller;

import com.museum.ticketsystem.model.Order;
import com.museum.ticketsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/order")
public class ViewController {

    @Autowired
    private BookingService bookingService;

    // 根据用户ID获取订单
    @GetMapping("/view/{userId}")  // 确保这个路径正确
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = bookingService.getOrdersByUser(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build(); // 返回204 No Content
        }
        return ResponseEntity.ok(orders); // 返回200 OK
    }

}


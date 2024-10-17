package com.museum.ticketsystem.controller;

import com.museum.ticketsystem.model.OrderItem;
import com.museum.ticketsystem.model.Ticket;
import com.museum.ticketsystem.model.User;
import com.museum.ticketsystem.repository.TicketRepository;
import com.museum.ticketsystem.service.BookingService;
import com.museum.ticketsystem.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketRepository ticketRepository;

    // 创建新订单
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderRequest orderRequest) {
        Map<String, String> response = new HashMap<>();

        // 检查用户
        User user = userService.getUserById(orderRequest.getUserId());
        if (user == null) {
            response.put("message", "User not found!");
            return ResponseEntity.badRequest().body(response);  // 返回 400 错误并包含错误信息
        }

        // 获取票种信息
        Ticket ticket = ticketRepository.findById(orderRequest.getTicketId()).orElse(null);
        if (ticket == null) {
            response.put("message", "Ticket not found!");
            return ResponseEntity.badRequest().body(response);
        }

        // 解析订单日期字符串为 Date 对象
        Date orderDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            orderDate = dateFormat.parse(orderRequest.getOrderDate());  // 将字符串转换为 Date
        } catch (ParseException e) {
            response.put("message", "Invalid date format!");
            return ResponseEntity.badRequest().body(response);  // 返回 400 错误并包含错误信息
        }

        // 生成订单项
        List<OrderItem> items = orderRequest.getOrderItems(ticket);
        if (items.isEmpty()) {
            response.put("message", "No order items provided!");
            return ResponseEntity.badRequest().body(response);
        }

        // 调用订票方法
        String result = bookingService.bookTickets(orderRequest.getUserId(),
                orderRequest.getTicketId(),
                new SimpleDateFormat("yyyy-MM-dd").format(orderDate), // 转换为字符串
                orderRequest.getQuantity(),
                items); // 使用生成的订单项

        response.put("message", result);
        return ResponseEntity.ok(response); // 返回响应
    }

    @Setter
    @Getter
    public static class OrderRequest {
        private Long userId;  // 用户ID
        private Long ticketId;  // 票种ID
        private int quantity;  // 订票数量
        private String orderDate;  // 订票日期
        private List<Visitor> visitors = new ArrayList<>();  // 游客信息

        // 根据访客信息生成 OrderItems
        public List<OrderItem> getOrderItems(Ticket ticket) {
            List<OrderItem> items = new ArrayList<>();
            for (int i = 0; i < quantity; i++) {
                OrderItem item = new OrderItem();
                item.setTicket(ticket);  // 设置票种
                item.setQuantity(1);  // 每个 OrderItem 表示一张票

                // 如果有游客信息则设置
                if (i < visitors.size()) {
                    Visitor visitor = visitors.get(i);
                    item.setVisitorName(visitor.getVisitorName());  // 设置游客姓名
                    item.setVisitorIDNumber(visitor.getVisitorIDNumber());  // 设置游客身份证号
                }

                items.add(item);  // 添加到订单项列表
            }
            return items;
        }
    }

    @Setter
    @Getter
    public static class Visitor {
        private String visitorName;
        private String visitorIDNumber;
    }
}

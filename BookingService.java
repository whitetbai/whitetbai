package com.museum.ticketsystem.service;

import com.museum.ticketsystem.model.*;
import com.museum.ticketsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketDateRepository ticketDateRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 核心的订票业务逻辑
    public String bookTickets(Long userId, Long ticketId,  String orderDateStr, int quantity, List<OrderItem> items) {
        // Step 1: 验证用户
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "User not found!";
        }
        // Step 2: 解析订单日期字符串为 java.sql.Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate;
        try {
            // 将传入的字符串解析为 java.sql.Date
            orderDate = new Date(dateFormat.parse(orderDateStr).getTime());
        } catch (ParseException e) {
            return "Invalid date format!";
        }

        // Step 2: 查找门票
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            return "Ticket type not found!";
        }

        // Step 3: 查找余票信息
        TicketDate ticketDate = ticketDateRepository.findByTicketTicketIdAndDate(ticketId, orderDate);
        if (ticketDate == null || ticketDate.getAvailableQuantity() < quantity) {
            return "Not enough tickets available!";
        }
        System.out.println("Found TicketDate: " + ticketDate);

        // Step 4: 创建订单
        Order order = new Order();
        order.setUser(user);  // 设置用户
        order.setOrderDate(new Date());  // 设置订单日期
        double totalPrice = ticket.getPrice() * quantity;  // 计算总价
        order.setTotalPrice(totalPrice);  // 设置总价
        orderRepository.save(order);  // 保存订单

        // Step 5: 创建订单明细
        for (OrderItem item : items) {
            item.setOrder(order);  // 关联订单
            item.setTicket(ticket);  // 关联门票
            orderItemRepository.save(item);  // 保存订单项
        }
        System.out.println("Updating TicketDate for ticketId: " + ticketId + " and date: " + orderDate);
        System.out.println("Before update - ReserveQuantity: " + ticketDate.getReserveQuantity());
        // 更新票务信息
        ticketDate.setTotalQuantity(ticketDate.getTotalQuantity() - quantity); // 减少总票数
        ticketDate.setReserveQuantity(ticketDate.getReserveQuantity() + quantity); // 增加已预定数量
        // 保存更新后的记录
        ticketDateRepository.save(ticketDate);
        System.out.println("After update - ReserveQuantity: " + ticketDate.getReserveQuantity());
        // 打印可用票数
        Integer availableQuantity = ticketDate.getAvailableQuantity();
        System.out.println("可用票数: " + availableQuantity);

        // 返回成功消息
        return "Booking successful!";
    }
    public void initializeTicketDates(int daysToInitialize) {
        List<Ticket> tickets = ticketRepository.findAll();
        Date currentDate = new Date();  // 使用当前日期作为基准

        // 初始化从今天开始的未来几天的票务信息
        for (int i = 0; i < daysToInitialize; i++) {
            // 获取从当前日期开始的未来第 i 天
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);  // 基于当前日期
            calendar.add(Calendar.DAY_OF_YEAR, i);  // 增加天数
            Date futureDate = calendar.getTime();  // 生成未来的日期

            // 为每个票种创建或更新该日期的票务信息
            for (Ticket ticket : tickets) {
                TicketDate ticketDate = ticketDateRepository.findByTicketTicketIdAndDate(ticket.getTicketId(), futureDate);

                // 如果该日期的票务信息不存在，则创建新的记录
                if (ticketDate == null) {
                    ticketDate = new TicketDate();
                    ticketDate.setTicket(ticket);
                    ticketDate.setDate(futureDate);
                    ticketDate.setTotalQuantity(100);  // 设置总票数
                    ticketDate.setReserveQuantity(0);  // 初始已预定数量为0

                    ticketDateRepository.save(ticketDate);  // 保存到数据库
                } else {
                    System.out.println("Ticket date already exists for ticket ID: " + ticket.getTicketId() + " on " + futureDate);
                }
            }
        }
    }






    // 根据用户ID查询所有订单
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUser_UserId(userId);  // 使用外键查询订单
    }

    public Order getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElse(null);  // 返回订单或 null
    }

    // 创建订单的方法
    public void createOrder(User user, List<OrderItem> items, double totalPrice) {
        Order order = new Order();
        order.setUser(user);  // 设置用户
        order.setTotalPrice(totalPrice);  // 设置订单总价
        order.setOrderDate(new Date());  // 设置订单日期
        orderRepository.save(order);  // 保存订单

        // 保存订单明细
        if (items != null) {
            for (OrderItem item : items) {
                // 确保每个 OrderItem 的 Ticket 已设置
                Ticket ticket = ticketRepository.findById(item.getTicket().getTicketId()).orElse(null);
                if (ticket == null) {
                    throw new IllegalArgumentException("Ticket not found for item");
                }
                item.setOrder(order);  // 关联订单
                item.setTicket(ticket);  // 关联正确的 Ticket
                orderItemRepository.save(item);  // 保存订单项
            }
        }
    }

}

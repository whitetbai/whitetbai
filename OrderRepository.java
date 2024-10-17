package com.museum.ticketsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.museum.ticketsystem.model.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 使用嵌套属性 user.userId 进行查询
    List<Order> findByUser_UserId(Long userId);
}

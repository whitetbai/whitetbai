package com.museum.ticketsystem.repository;

import com.museum.ticketsystem.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}

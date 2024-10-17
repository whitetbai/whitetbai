package com.museum.ticketsystem.repository;

import com.museum.ticketsystem.model.TicketDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TicketDateRepository extends JpaRepository<TicketDate, Long> {
    List<TicketDate> findByDate(Date date);
    TicketDate findByTicketTicketIdAndDate(Long ticketId, Date date);
}

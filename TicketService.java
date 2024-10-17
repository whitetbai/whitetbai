package com.museum.ticketsystem.service;

import com.museum.ticketsystem.model.Ticket;
import com.museum.ticketsystem.model.TicketDate;
import com.museum.ticketsystem.repository.TicketDateRepository;
import com.museum.ticketsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketDateRepository ticketDateRepository;

    // 获取所有门票
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // 获取指定日期的余票信息
    public List<TicketDate> getAvailableTicketsByDate(Date date) {
        return ticketDateRepository.findByDate(date);
    }
}

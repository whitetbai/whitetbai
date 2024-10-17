package com.museum.ticketsystem.controller;

import com.museum.ticketsystem.model.Ticket;
import com.museum.ticketsystem.model.TicketDate;
import com.museum.ticketsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // 获取所有门票信息
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // 获取指定日期的余票
    @GetMapping("/availability")
    public List<TicketDate> getTicketAvailability(@RequestParam Date date) {
        return ticketService.getAvailableTicketsByDate(date);
    }
}

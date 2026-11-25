package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.ServiceTicketRequest;
import com.examly.springapp.model.ServiceTicket;

public interface ServiceTicketService {

    ServiceTicket createTicket(ServiceTicketRequest request);

    List<ServiceTicket> getAllTickets();

    ServiceTicket getTicketById(Long id);

    ServiceTicket updateTicket(Long id, ServiceTicketRequest request);

    ServiceTicket completeTicket(Long id);

    void deleteTicket(Long id);
}

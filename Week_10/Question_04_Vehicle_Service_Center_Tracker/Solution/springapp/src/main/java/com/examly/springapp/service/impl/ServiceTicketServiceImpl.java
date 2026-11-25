package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ServiceTicketRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.ServiceTicket;
import com.examly.springapp.repository.ServiceTicketRepository;
import com.examly.springapp.service.ServiceTicketService;

@Service
public class ServiceTicketServiceImpl implements ServiceTicketService {

    private final ServiceTicketRepository ticketRepository;

    public ServiceTicketServiceImpl(ServiceTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public ServiceTicket createTicket(ServiceTicketRequest request) {
        ServiceTicket ticket = new ServiceTicket();
        copy(request, ticket);
        ticket.setStatus("OPEN");
        return ticketRepository.save(ticket);
    }

    @Override
    public List<ServiceTicket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public ServiceTicket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service ticket not found with id: " + id));
    }

    @Override
    public ServiceTicket updateTicket(Long id, ServiceTicketRequest request) {
        ServiceTicket ticket = getTicketById(id);
        copy(request, ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public ServiceTicket completeTicket(Long id) {
        ServiceTicket ticket = getTicketById(id);
        ticket.setStatus("COMPLETED");
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Long id) {
        ServiceTicket ticket = getTicketById(id);
        ticketRepository.delete(ticket);
    }

    private void copy(ServiceTicketRequest request, ServiceTicket ticket) {
        ticket.setVehicleNumber(request.getVehicleNumber());
        ticket.setOwnerName(request.getOwnerName());
        ticket.setServiceType(request.getServiceType());
        ticket.setEstimatedCost(request.getEstimatedCost());
    }
}

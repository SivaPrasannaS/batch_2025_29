package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.AppointmentRequest;
import com.examly.springapp.exception.AppointmentStateException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Appointment;
import com.examly.springapp.repository.AppointmentRepository;
import com.examly.springapp.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment bookAppointment(AppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setPetName(request.getPetName());
        appointment.setPetType(request.getPetType());
        appointment.setOwnerName(request.getOwnerName());
        appointment.setVetName(request.getVetName());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setStatus("SCHEDULED");
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    @Override
    public List<Appointment> getAppointmentsByVet(String vetName) {
        return appointmentRepository.findByVetName(vetName);
    }

    @Override
    public Appointment cancelAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        if ("CANCELLED".equals(appointment.getStatus())) {
            throw new AppointmentStateException("Appointment " + id + " is already cancelled");
        }
        appointment.setStatus("CANCELLED");
        return appointmentRepository.save(appointment);
    }
}

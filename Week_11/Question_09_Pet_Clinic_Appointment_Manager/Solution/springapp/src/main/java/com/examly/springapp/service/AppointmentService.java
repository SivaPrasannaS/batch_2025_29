package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.AppointmentRequest;
import com.examly.springapp.model.Appointment;

public interface AppointmentService {

    Appointment bookAppointment(AppointmentRequest request);

    Appointment getAppointmentById(Long id);

    List<Appointment> getAppointmentsByVet(String vetName);

    Appointment cancelAppointment(Long id);
}

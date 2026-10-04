package com.petclinic.petclinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.petclinic.petclinic.entity.Appointment;
import com.petclinic.petclinic.repository.AppointmentRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Object[]> getAppointmentDetails() {
        return appointmentRepository.findAppointmentDetails();
    }

    public void bookPetAppointment(
            Integer petId,
            Integer vetId,
            java.sql.Date appointmentDate,
            java.sql.Time appointmentTime,
            String reason) {

        appointmentRepository.bookPetAppointment(
                petId,
                vetId,
                appointmentDate,
                appointmentTime,
                reason
        );
    }
}
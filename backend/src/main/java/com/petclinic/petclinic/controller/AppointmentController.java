package com.petclinic.petclinic.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petclinic.petclinic.entity.Appointment;
import com.petclinic.petclinic.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Get all appointments
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Get appointment details using JOIN
    @GetMapping("/details")
    public List<Object[]> getAppointmentDetails() {
        return appointmentService.getAppointmentDetails();
    }

    // Book appointment using stored procedure
    @PostMapping("/book")
    public String bookPetAppointment(
            @RequestBody BookAppointmentRequest request) {

        appointmentService.bookPetAppointment(
                request.getPetId(),
                request.getVetId(),
                java.sql.Date.valueOf(request.getAppointmentDate()),
                java.sql.Time.valueOf(request.getAppointmentTime()),
                request.getReason()
        );

        return "Appointment booked successfully";
    }
}
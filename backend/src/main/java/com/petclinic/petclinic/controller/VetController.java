package com.petclinic.petclinic.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petclinic.petclinic.entity.Vet;
import com.petclinic.petclinic.service.VetService;

@RestController
@RequestMapping("/api/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    // Get all vets
    @GetMapping
    public List<Vet> getAllVets() {
        return vetService.getAllVets();
    }

    // Find vets with appointments above average
    @GetMapping("/above-average-appointments")
    public List<Object[]> getVetsAboveAverageAppointments() {
        return vetService.getVetsAboveAverageAppointments();
    }

    // Calculate consultation fee using MySQL function
    @GetMapping("/{vetId}/fee")
    public Double calculateConsultationFee(@PathVariable Integer vetId) {
        return vetService.calculateConsultationFee(vetId);
    }
}
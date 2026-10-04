package com.petclinic.petclinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.petclinic.petclinic.entity.Vet;
import com.petclinic.petclinic.repository.VetRepository;

@Service
public class VetService {

    private final VetRepository vetRepository;

    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    // Get all vets
    public List<Vet> getAllVets() {
        return vetRepository.findAll();
    }

    // Find vets with appointments above average
    public List<Object[]> getVetsAboveAverageAppointments() {
        return vetRepository.findVetsAboveAverageAppointments();
    }

    // Calculate consultation fee using MySQL function
    public Double calculateConsultationFee(Integer vetId) {
        return vetRepository.calculateConsultationFee(vetId);
    }
}
package com.petclinic.petclinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.petclinic.petclinic.entity.Pet;
import com.petclinic.petclinic.repository.PetRepository;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}
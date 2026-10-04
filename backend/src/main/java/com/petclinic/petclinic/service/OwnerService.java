package com.petclinic.petclinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.petclinic.petclinic.entity.Owner;
import com.petclinic.petclinic.repository.OwnerRepository;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }
}
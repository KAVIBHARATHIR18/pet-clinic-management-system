package com.petclinic.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petclinic.petclinic.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {

}
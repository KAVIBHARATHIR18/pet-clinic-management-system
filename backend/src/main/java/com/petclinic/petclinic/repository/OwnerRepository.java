package com.petclinic.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petclinic.petclinic.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {

}
package com.petclinic.petclinic.repository;

import com.petclinic.petclinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query(value = """
        SELECT
            a.appointment_id,
            a.appointment_date,
            a.appointment_time,
            a.reason,
            a.status,
            p.pet_name,
            p.species,
            o.owner_name,
            v.vet_name,
            v.specialization
        FROM appointments a
        JOIN pets p
            ON a.pet_id = p.pet_id
        JOIN owners o
            ON p.owner_id = o.owner_id
        JOIN vets v
            ON a.vet_id = v.vet_id
        """, nativeQuery = true)
    List<Object[]> findAppointmentDetails();

    @Procedure(procedureName = "BookPetAppointment")
    void bookPetAppointment(
            Integer p_pet_id,
            Integer p_vet_id,
            java.sql.Date p_appointment_date,
            java.sql.Time p_appointment_time,
            String p_reason
    );
}
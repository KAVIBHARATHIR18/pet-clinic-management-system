package com.petclinic.petclinic.repository;

import com.petclinic.petclinic.entity.Vet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VetRepository extends JpaRepository<Vet, Integer> {

    // Subquery: Find vets with appointments above average
    @Query(value = """
        SELECT
            v.vet_id,
            v.vet_name,
            COUNT(a.appointment_id) AS appointment_count
        FROM vets v
        JOIN appointments a
            ON v.vet_id = a.vet_id
        GROUP BY v.vet_id, v.vet_name
        HAVING COUNT(a.appointment_id) >
        (
            SELECT AVG(appointment_count)
            FROM (
                SELECT
                    vet_id,
                    COUNT(appointment_id) AS appointment_count
                FROM appointments
                GROUP BY vet_id
            ) AS vet_counts
        )
        """, nativeQuery = true)
    List<Object[]> findVetsAboveAverageAppointments();

    // MySQL function: Calculate consultation fee
    @Query(value = "SELECT CalculateConsultationFee(?1)", nativeQuery = true)
    Double calculateConsultationFee(Integer vetId);
}
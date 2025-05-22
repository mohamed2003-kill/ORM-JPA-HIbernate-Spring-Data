package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

    List<Patient> findByNomContainsIgnoreCase(String name);

    @Query("select p from Patient as p where p.nom like " +
            "%:x% ")
    Optional<Patient> findOnePatientByName(@Param("x") String nom);

    @Modifying
    @Transactional
    @Query("DELETE FROM Patient p WHERE p.nom LIKE %:x%")
    void deleteOnePatientByName(@Param("x") String nom);
}

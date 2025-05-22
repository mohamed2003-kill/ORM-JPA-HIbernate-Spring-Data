package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;
import java.sql.Date;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private PatientRepo patientRepo;

    public static void main(String[] args) {
        ApplicationContext ctx = (ApplicationContext) SpringApplication.run(DemoApplication.class, args);
//        patientRepo.save(new Patient(2,"Mohamed Aharram",new Date(2014, 12, 12),true,1000));

    }

    public void run(String... args) throws Exception {


        patientRepo.save(Patient.builder().nom("Abdallah Hechane").dateNaissance(Date.valueOf(LocalDate.of(2024, 12, 12))).malade(true).score(1000).build());
        patientRepo.save(Patient.builder().nom("Aharram Mohamed").dateNaissance(Date.valueOf(LocalDate.of(2014, 12, 12))).malade(true).score(1000).build());
        patientRepo.save(Patient.builder().nom("Yassine Elaichi").dateNaissance(Date.valueOf(LocalDate.of(2020, 12, 12))).malade(true).score(1000).build());

        List<Patient> patients = patientRepo.findAll();
        for (Patient p : patients) {
            System.out.println(p.toString());
        }
        Optional<Patient> patient = patientRepo.findById(1);
        patient.ifPresent(p -> System.out.println("Patient is Found :" + p.toString()));
        List<Patient> FoundPatients = patientRepo.findByNomContainsIgnoreCase("mohamed");
        FoundPatients.forEach(p -> System.out.println("Patient is Found :" + p.toString()));
        Optional<Patient> toChangePatient = patientRepo.findOnePatientByName("Mohamed");
        if (toChangePatient.isPresent()) {
            Patient localPatient = toChangePatient.get();
            System.out.println("Patient Date Before Changing : " + localPatient.getDateNaissance());
            localPatient.setDateNaissance(Date.valueOf(LocalDate.of(2025, 10, 5)));
            patientRepo.save(localPatient);
            System.out.println("Patient Date After Changing : " + localPatient.getDateNaissance());
            patientRepo.deleteOnePatientByName("Mohamed");

            System.out.println("Patient Deleted Successfully");
        } else {
            System.out.println("Patient Not Found");
        }
        Patient toChangePatient2 = patientRepo.findOnePatientByName("Abd").get();
        toChangePatient2.setMalade(false);
        patientRepo.save(toChangePatient2);
    }
}

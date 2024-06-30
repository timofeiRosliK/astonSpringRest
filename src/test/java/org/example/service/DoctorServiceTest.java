package org.example.service;

import org.example.config.TestContainerConfig;
import org.example.dto.DoctorResponseDto;
import org.example.exceptions.DoctorNotFoundException;
import org.example.model.Doctor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContainerConfig.class})
@WebAppConfiguration
@Sql(scripts = {"classpath:insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:delete.sql"}, executionPhase = AFTER_TEST_METHOD)

class DoctorServiceTest {

    @Autowired
    private DoctorService doctorService;

    @Test
    void saveDoctor_WhenSaveDoctor_DoctorSaved() {

        Doctor doctor = new Doctor(1, "Valya", "Ivanova", "surgeon");
        DoctorResponseDto savedDoctor = doctorService.saveDoctor(doctor);

        Assertions.assertEquals(1, savedDoctor.getId());
        Assertions.assertEquals("Valya", savedDoctor.getFirstName());
        Assertions.assertEquals("Ivanova", savedDoctor.getLastName());
        Assertions.assertEquals("surgeon", savedDoctor.getSpecialization());
    }

    @Test
    void getDoctor_WhenGetDoctor_DoctorGot() {
        int doctorId = 2;

        DoctorResponseDto doctor = doctorService.getDoctorById(doctorId);

        Assertions.assertEquals(2, doctor.getId());
        Assertions.assertEquals("Jane", doctor.getFirstName());
        Assertions.assertEquals("Smith", doctor.getLastName());
        Assertions.assertEquals("GP", doctor.getSpecialization());
    }

    @Test
    void getDoctor_WhenDoctorFind_ThrowDoctorNotFoundException() {
        int doctorId = 500;

        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(doctorId));
    }

    @Test
    void updateDoctor_WhenUpdateDoctor_DoctorUpdated() {
        int doctorId = 3;
        Doctor doctor = new Doctor(doctorId, "Ivan", "Seznev", "oncologist");

        DoctorResponseDto updatedDoctor = doctorService.updateDoctorById(doctorId, doctor);

        Assertions.assertEquals(3, updatedDoctor.getId());
        Assertions.assertEquals("Ivan", updatedDoctor.getFirstName());
        Assertions.assertEquals("Seznev", updatedDoctor.getLastName());
        Assertions.assertEquals("oncologist", updatedDoctor.getSpecialization());
    }

    @Test
    void updateDoctor_WhenDoctorIsNull_ThrowDoctorNotFoundException() {
        int doctorId = 501;

        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(doctorId));
    }

    @Test
    void deleteDoctor_WhenDeleteDoctor_DoctorDeleted() {
        int doctorId = 1;

        doctorService.removeDoctor(doctorId);

        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById((doctorId)));
    }
}


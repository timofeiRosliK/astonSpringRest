package org.example.web;

import org.example.config.TestContainerConfig;
import org.example.dto.PatientCreateDto;
import org.example.dto.PatientUpdateDto;
import org.example.dto.TreatmentCreateDto;
import org.example.dto.TreatmentUpdateDto;
import org.example.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContainerConfig.class})
@WebAppConfiguration
@Sql(scripts = {"classpath:insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:delete.sql"}, executionPhase = AFTER_TEST_METHOD)
class PatientControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void save_testSavePatientData_PatientSaved() throws Exception {
        String name = "Katya";
        String lastName = "Petrova";
        Gender gender = Gender.WOMAN;
        int diagnosisId = 1;
        int doctorId = 1;

        PatientCreateDto patientCreateDto = new PatientCreateDto();
        patientCreateDto.setFirstName(name);
        patientCreateDto.setLastName(lastName);
        patientCreateDto.setGender(gender);
        patientCreateDto.setDiagnosisId(diagnosisId);
        patientCreateDto.setDoctorId(doctorId);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void get_testGetPatientById_PatientGot() throws Exception {
        int id = 1;

        mockMvc.perform(get("/patients/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_testUpdatePatientData_UpdatedPatient() throws Exception {
        int id = 1;
        String name = "Masha";
        String lastName = "Ivanova";
        Gender gender = Gender.WOMAN;

        PatientUpdateDto updateDto = new PatientUpdateDto();
        updateDto.setFirstName(name);
        updateDto.setLastName(lastName);
        updateDto.setGender(gender);

        mockMvc.perform(put("/patients/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void delete_testDeletePatientData_DeletedData() throws Exception {
        int patientId = 1;

        mockMvc.perform(delete("/patients/" + patientId))
                .andExpect(status().isOk());
    }
}
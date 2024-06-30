package org.example.web;

import org.example.config.TestContainerConfig;
import org.example.dto.DoctorCreateDto;
import org.example.dto.DoctorUpdateDto;
import org.example.dto.TreatmentUpdateDto;
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
class DoctorControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    void save_testSaveDoctorData_DoctorSaved() throws Exception {
        String name = "Ivan";
        String lastName = "Ivanova";
        String specialization = "oncologist";

        DoctorCreateDto doctorCreateDto = new DoctorCreateDto();
        doctorCreateDto.setFirstName(name);
        doctorCreateDto.setLastName(lastName);
        doctorCreateDto.setSpecialization(specialization);

        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void get_testGetDoctorById_DoctorGot() throws Exception {
        int id = 1;

        mockMvc.perform(get("/doctors/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_testUpdateDoctorData_UpdatedDoctor() throws Exception {
        int id = 1;
        String name = "Alex";
        String lastName = "By";
        String specialization = "GP";

        DoctorUpdateDto updateDto = new DoctorUpdateDto();
        updateDto.setFirstName(name);
        updateDto.setLastName(lastName);
        updateDto.setSpecialization(specialization);



        mockMvc.perform(put("/doctors/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void delete_testDeleteDoctorData_DeletedData() throws Exception {
        int doctorId = 1;

        mockMvc.perform(delete("/doctors/" + doctorId))
                .andExpect(status().isOk());
    }

}
package org.example.web;

import org.example.config.TestContainerConfig;
import org.example.dto.DiagnosisCreateDto;
import org.example.dto.DiagnosisUpdateDto;
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
class DiagnosisControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private DiagnosisController diagnosisController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(diagnosisController).build();
    }

    @Test
    void save_testSaveDiagnosisData_DiagnosisSaved() throws Exception {
        int treatmentId = 1;
        String name = "cancer";


        DiagnosisCreateDto diagnosisCreateDto = new DiagnosisCreateDto();
        diagnosisCreateDto.setName(name);
        diagnosisCreateDto.setTreatmentId(treatmentId);

        mockMvc.perform(post("/diagnoses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diagnosisCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void get_testGetDiagnosisById_DiagnosisGot() throws Exception {
        int id = 1;

        mockMvc.perform(get("/diagnoses/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_testUpdateDiagnosisData_UpdatedDiagnosis() throws Exception {
        int diagnosisId = 1;
        String name = "cold";

        DiagnosisUpdateDto updateDto = new DiagnosisUpdateDto();
        updateDto.setName(name);


        mockMvc.perform(put("/diagnoses/" + diagnosisId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void delete_testDeleteDiagnosisData_DeletedData() throws Exception {
        int diagnosisId = 1;

        mockMvc.perform(delete("/diagnoses/" + diagnosisId))
                .andExpect(status().isOk());
    }
}

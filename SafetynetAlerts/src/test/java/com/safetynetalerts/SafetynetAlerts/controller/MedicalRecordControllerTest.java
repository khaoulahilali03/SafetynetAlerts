package com.safetynetalerts.SafetynetAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getAllMedicalRecordTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecord"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(23)));
    }

    @Test
    public void createMedicalRecordTest() throws Exception{
        MedicalRecord medicalRecord = new MedicalRecord("Khaoula", "Hilali","12/03/1978", Collections.singletonList("amoxicilline:100mg"), Collections.singletonList(""));
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord")
                .content(objectMapper.writeValueAsBytes(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(24)));
    }

    @Test
    public void createMedicalRecordTest_withExistingMedicalRecord() throws Exception{
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd","03/06/1984", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord")
                .content(objectMapper.writeValueAsString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(23)));
    }

    @Test
    public void updateMedicalRecordTest() throws Exception{
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd","12/03/1978", Collections.singletonList("amoxicilline:100mg"), Collections.singletonList(""));
        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord/John/Boyd")
                .content(objectMapper.writeValueAsString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateMedicalRecordTest_withNoExistingMedicalRecord() throws Exception{
        MedicalRecord medicalRecord = new MedicalRecord("Khaoula", "Hilali","12/03/1986", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord/Khaoula/Hilali")
                        .content(objectMapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMedicalRecordTest() throws Exception{
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd","03/06/1984", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord/John/Boyd")
                .content(objectMapper.writeValueAsString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(22)));
    }

    @Test
    public void deleteMedicalRecordTest_withNoExistingMedicalRecord() throws Exception{
        MedicalRecord medicalRecord = new MedicalRecord("Khaoula", "Hilali","12/03/1986", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord/Khaoula/Hilali")
                        .content(objectMapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(23)));
    }
    @Test
    public void getChildAlertTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/childalert?address=1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.children.size()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familyMembers.size()", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.children[0].age").value(10));
    }

    @Test
    public void getChildAlertTest_withNoExistingChild() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/childalert?address=29 15th St"))
                .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.children.length()",is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familyMembers.size()", is(0)));
    }

    @Test
    public void getChildAlertTest_withAddressNotFound() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/childalert?address=1509 Culver"))
                .andExpect(status().isInternalServerError());

    }
}

package com.safetynetalerts.SafetynetAlerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllFireStationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(13)));
    }

    @Test
    public void createFireStationTest() throws Exception {
        FireStation fireStation = new FireStation("22 rue pasteur", "5");
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .content(objectMapper.writeValueAsString(fireStation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(14)));
    }

    @Test
    public void createFireStationTest_withExistingFireStation() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .content(objectMapper.writeValueAsString(fireStation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(13)));
    }

    @Test
    public void updateFireStationTest() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "2");
        mockMvc.perform(MockMvcRequestBuilders.put("/firestation/1509 Culver St")
                .content(objectMapper.writeValueAsString(fireStation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFireStationTest_withNoExistingFireStation() throws Exception{
        FireStation fireStation = new FireStation("22 rue pasteur", "5");
        mockMvc.perform(MockMvcRequestBuilders.put("/firestation/22 rue pasteur")
                        .content(objectMapper.writeValueAsString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFireStationTest() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/1509 Culver St")
                .content(objectMapper.writeValueAsString(fireStation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(12)));
    }

    @Test
    public void deleteFireStationTest_withNoExistingFireStation() throws Exception{
        FireStation fireStation = new FireStation("22 rue pasteur", "5");
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/22 rue pasteur")
                        .content(objectMapper.writeValueAsString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(13)));
    }

    @Test
    public void getPhoneNumberForAStationTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/phonealert?numberStation=1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2]").value("841-874-7462"));
    }

    @Test
    public void getPhoneNumberForAStationTest_withNoExistingNumberStation() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/phonealert?numberStation=19"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(0)));
    }

    @Test
    public void findStationByAddressTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/fire?address=1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personWithMedicalRecordDtoList.size()",is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stationsNumbers[0]").value("3"));
    }

    @Test
    public void findStationByAddressTest_withNoExistingAddress() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/fire?address=22 rue pasteur"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stationsNumbers.size()",is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personWithMedicalRecordDtoList.size()",is(0)));
    }

    @Test
    public void findPersonsByStationNumberTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/firestationsn?numberStation=2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personSet.size()",is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adult_count").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.child_count").value("1"));
    }

    @Test
    public void findPersonsByStationNUmberTest_withNoExistingNumberStation() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/firestationsn?numberStation=10"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.personSet.size()",is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adult_count").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.child_count").value("0"));
    }

    @Test
    public void findAllPersonByStationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flood?stationsNumber=1,2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(6)));
    }

    @Test
    public void findAllPersonByStationTest_withNoExistingNumberStation() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/flood?stationsNumber=10,20"))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(0)));
    }
}

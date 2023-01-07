package com.safetynetalerts.SafetynetAlerts.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void getAllPersonsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(23)));
    }

    @Test
    public void createPersonTest() throws Exception {
        Person person = new Person("Khaoula","Hilali","22 rue pasteur","Paris","75000","234-879-0765","khilali@email.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .content(objectMapper.writeValueAsString(person))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(24)));
    }

    @Test
    public void createPersonTest_withExistingPerson() throws Exception {
        Person person = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .content(objectMapper.writeValueAsString(person))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(23)));
    }

    @Test
    public void updatePersonTest() throws Exception {
        Person person = new Person("John","Boyd","1509 Culver Street","Culver","97451","841-874-6512","jaboyd@email.com");
        mockMvc.perform(MockMvcRequestBuilders.put("/person/John/Boyd")
                .content(objectMapper.writeValueAsString(person))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePersonTest_withNoExistingPerson() throws Exception{
        Person person = new Person("Khaoula","Hilali","1509 Culver Street","Culver","97451","841-874-6512","jaboyd@email.com");
        mockMvc.perform(MockMvcRequestBuilders.put("/person/Khaoula/Hilali")
                .content(objectMapper.writeValueAsString(person))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePersonTest() throws Exception {
        Person person = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/person/John/Boyd")
                .contentType(objectMapper.writeValueAsString(person))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(22)));
    }

    @Test
    public void deletePersonTest_withNoExistingPerson() throws Exception{
        Person person = new Person("Khaoula","Hilali","1509 Culver Street","Culver","97451","841-874-6512","jaboyd@email.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/person/Khaoula/Hilali")
                .contentType(objectMapper.writeValueAsString(person))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(23)));
    }

    @Test
    public void getAllEmailsByCityTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/communityemail?city=Culver"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",is(23)));
    }

    @Test
    public void getPersonInfoTestForExistingName() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/personinfo?firstname=John&lastname=Boyd"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(7)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address",is("1509 Culver St")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(38))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications.size()", is (2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies.size()",is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("jaboyd@email.com")));
    }

    @Test
    public void getPersonInfoTestForNoExistingName() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/personinfo?firstname=John&lastname=Boy"))
                .andExpect(status().isNotFound());
    }
}

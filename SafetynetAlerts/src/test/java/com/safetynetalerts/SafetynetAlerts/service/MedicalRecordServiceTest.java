package com.safetynetalerts.SafetynetAlerts.service;

import com.safetynetalerts.SafetynetAlerts.model.DTO.ChildAlertDto;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import com.safetynetalerts.SafetynetAlerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    MedicalRecordService medicalRecordService;
    List<MedicalRecord> medicalRecordList;

    @BeforeEach
    public void setUpTest(){
        medicalRecordList = new ArrayList<>();
    }

    @Test
    public void getAllMedicalRecordsTest() {
        MedicalRecord mr1 = new MedicalRecord("John", "Boyd", "03/06/1984", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        MedicalRecord mr2 = new MedicalRecord("Jonanathan", "Marrack", "01/03/1989", Collections.singletonList(""), Collections.singletonList(""));
        medicalRecordList.add(mr1);
        medicalRecordList.add(mr2);
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecordList);
        List<MedicalRecord> medicalRecordListResult = medicalRecordService.getAllMedicalRecords();
        assertEquals(medicalRecordListResult, medicalRecordList);
    }

    @Test
    public void findMedicalRecordByNameTest(){
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd","03/06/1984", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        String firstname = medicalRecord.getFirstName();
        String lastname = medicalRecord.getLastName();
        when(medicalRecordRepository.findMedicalRecordByName(firstname,lastname)).thenReturn(medicalRecord);
        MedicalRecord medicalRecordResult = medicalRecordService.findMedicalRecordByName(firstname,lastname);
        assertEquals(medicalRecordResult, medicalRecord);
    }

    @Test
    public void findMedicalRecordByNameTest_withNoExistingName(){
        String firstname = "Khaoula";
        String lastname = "Hilali";
        when(medicalRecordRepository.findMedicalRecordByName(firstname,lastname)).thenReturn(null);
        assertNull(medicalRecordService.findMedicalRecordByName(firstname,lastname));
    }

    @Test
    public void createMedicalRecordTest(){
        MedicalRecord medicalRecord = new MedicalRecord("Khaoula", "Hilali","12/03/1978", Collections.singletonList("amoxicilline:100mg"), Collections.singletonList(""));
        when(medicalRecordRepository.addMedicalRecord(medicalRecord)).thenReturn(medicalRecordList);
        List<MedicalRecord> medicalRecordListResult = medicalRecordService.createMedicalRecord(medicalRecord);
        assertEquals(medicalRecordListResult, medicalRecordList);
    }

    @Test
    public void createMedicalRecordTest_withExistingMedicalRecord(){
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd","03/06/1984", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        when(medicalRecordRepository.addMedicalRecord(medicalRecord)).thenReturn(null);
        assertNull(medicalRecordService.createMedicalRecord(medicalRecord));
    }

    @Test
    public void updateMedicalRecordTest(){
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd","12/03/1978", Collections.singletonList("amoxicilline:100mg"), Collections.singletonList(""));
        when(medicalRecordRepository.saveMedicalRecord(medicalRecord)).thenReturn(medicalRecord);
        MedicalRecord medicalRecordResult = medicalRecordService.updateMedicalRecord(medicalRecord);
        assertEquals(medicalRecordResult,medicalRecord);
    }

    @Test
    public void updateMedicalRecordTest_withNoExistingMedicalRecord(){
        MedicalRecord medicalRecord = new MedicalRecord("Khaoula", "Hilali","12/03/1986", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        when(medicalRecordRepository.saveMedicalRecord(medicalRecord)).thenReturn(null);
        assertNull(medicalRecordService.updateMedicalRecord(medicalRecord));
    }

    @Test
    public void deleteMedicalRecordTest(){
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd","03/06/1984", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        medicalRecordList.add(medicalRecord);
        String firstname = medicalRecord.getFirstName();
        String lastname = medicalRecord.getLastName();
        when(medicalRecordRepository.deleteMedicalRecord(firstname,lastname)).thenReturn(medicalRecordList);
        List<MedicalRecord> medicalRecordListResult = medicalRecordService.deleteMedicalRecord(firstname,lastname);
        assertEquals(medicalRecordListResult, medicalRecordList);
    }

    @Test
    public void deleteMedicalRecordTest_withNoExistingMedicalRecord(){
        MedicalRecord medicalRecord = new MedicalRecord("Khaoula", "Hilali","12/03/1986", Collections.singletonList("aznol:350mg,hydrapermazol:100mg"), Collections.singletonList("nillacilan"));
        String firstname = medicalRecord.getFirstName();
        String lastname = medicalRecord.getLastName();
        when(medicalRecordRepository.deleteMedicalRecord(firstname,lastname)).thenReturn(null);
        assertNull(medicalRecordService.deleteMedicalRecord(firstname,lastname));
    }

    @Test
    public void getChildAlertTest() throws ParseException {
        ChildAlertDto childAlertDto = new ChildAlertDto();
        Person person = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        String address = person.getAddress();
        when(medicalRecordRepository.getChildAlert(address)).thenReturn(childAlertDto);
        //Act
        ChildAlertDto childAlertDtoResult = medicalRecordService.getChildAlert(address);
        //Assert
        assertEquals(childAlertDtoResult, childAlertDto);
    }

    @Test
    public void getChildAlertTest_withNoExistingChild() throws ParseException {
        String address = "29 15th St";
        when(medicalRecordRepository.getChildAlert(address)).thenReturn(null);
        assertNull(medicalRecordService.getChildAlert(address));
    }

    @Test
    public void getChildAlertTest_withNoExistingAddress() throws ParseException {
        String address = "22 rue pasteur";
        when(medicalRecordRepository.getChildAlert(address)).thenReturn(null);
        assertNull(medicalRecordService.getChildAlert(address));
    }

}

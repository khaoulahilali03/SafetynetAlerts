package com.safetynetalerts.SafetynetAlerts.service;

import com.safetynetalerts.SafetynetAlerts.model.DTO.FireDto;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonCoveredByFireStationDto;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonWithMedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    FireStationRepository fireStationRepository;
    @InjectMocks
    FireStationService fireStationService;
    List<FireStation> fireStationList;
    @BeforeEach
    public void setUpTest(){
        fireStationList = new ArrayList<>();
    }

    @Test
    public void getAllFireStationsTest(){
        FireStation fs1=new FireStation("1509 Culver St","3");
        FireStation fs2 =new FireStation("644 Gershwin Cir","1");
        fireStationList.add(fs1);
        fireStationList.add(fs2);
        when(fireStationRepository.getAllFireStations()).thenReturn(fireStationList);
        List<FireStation> fireStationListResult = fireStationService.getAllFireStations();
        assertEquals(fireStationListResult, fireStationList);
    }

    @Test
    public void findFireStationByAddressTest(){
        FireStation fireStation = new FireStation("1509 Culver St","3");
        String address = fireStation.getAddress();
        when(fireStationRepository.findFireStationByAddress(address)).thenReturn(fireStation);
        FireStation fireStationResult = fireStationService.findFireStationByAddress(address);
        assertEquals(fireStationResult, fireStation);
    }

    @Test
    public void findFireStationByAddressTest_AddressDoesNotExist(){
        String address = "22 rue pasteur";
        when(fireStationRepository.findFireStationByAddress(address)).thenReturn(null);
        assertNull(fireStationService.findFireStationByAddress(address));
    }

    @Test
    public void createFireStationTest(){
        FireStation fireStation = new FireStation("22 rue pasteur", "4");
        fireStationList.add(fireStation);
        when(fireStationRepository.addFireStation(fireStation)).thenReturn(fireStationList);
        List<FireStation> fireStationListResult = fireStationService.createFireStation(fireStation);
        assertEquals(fireStationListResult, fireStationList);
    }

    @Test
    public void createFireStationTest_withExistingFireStation(){
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        when(fireStationRepository.addFireStation(fireStation)).thenReturn(null);
        assertNull(fireStationService.createFireStation(fireStation));
    }

    @Test
    public void updateFireStationTest(){
        FireStation fireStation = new FireStation("1509 Culver Street","3");
        when(fireStationRepository.saveFireStation(fireStation)).thenReturn(fireStation);
        FireStation fireStationResult = fireStationService.updateFireStation(fireStation);
        assertEquals(fireStationResult, fireStation);
    }

    @Test
    public void updateFireStationTest_withNoExistingFireStation(){
        FireStation fireStation = new FireStation("22 rue pasteur", "5");
        when(fireStationRepository.saveFireStation(fireStation)).thenReturn(null);
        assertNull(fireStationService.updateFireStation(fireStation));
    }

    @Test
    public void deleteFireStationTest(){
        FireStation fireStation = new FireStation("1509 Culver St","3");
        fireStationList.add(fireStation);
        String numberStation = fireStation.getStation();
        when(fireStationRepository.deleteFireStation(numberStation)).thenReturn(fireStationList);
        List<FireStation> fireStationListResult = fireStationService.deleteFireStation(numberStation);
        assertEquals(fireStationListResult, fireStationList);
    }

    @Test
    public void deleteFireStationTest_withNoExistingFireStation(){
        FireStation fireStation = new FireStation("22 rue pasteur","5");
        String numberStation = fireStation.getStation();
        when(fireStationRepository.deleteFireStation(numberStation)).thenReturn(null);
        assertNull(fireStationService.deleteFireStation(numberStation));
    }


    @Test
    public void getPhoneNumberForAStationTest(){
        LinkedHashSet<String> phonenumbers = new LinkedHashSet<>();
        FireStation fireStation = new FireStation("1509 Culver St","3");
        String numberStation = fireStation.getStation();
        when(fireStationRepository.getPhoneNumberForAStation(numberStation)).thenReturn(phonenumbers);
        LinkedHashSet<String> phonenumbersResult = fireStationService.getPhoneNumberForAStation(numberStation);
        assertEquals(phonenumbersResult, phonenumbers);
    }

    @Test
    public void getPhoneNumberForAStationTest_withNoExistingNumberStation(){
        String numberStation = "5";
        when(fireStationRepository.getPhoneNumberForAStation(numberStation)).thenReturn(null);
        assertNull(fireStationService.getPhoneNumberForAStation(numberStation));
    }

    @Test
    public void findStationByAddressTest() throws ParseException {
        FireDto fireDto = new FireDto();
        FireStation fireStation = new FireStation("1509 Culver St","3");
        String address = fireStation.getAddress();
        when(fireStationRepository.findStationByAddress(address)).thenReturn(fireDto);
        FireDto fireDtoResult = fireStationService.findStationByAddress(address);
        assertEquals(fireDtoResult,fireDto);

    }

    @Test
    public void findStationByAddressTest_withNoExistingAddress(){
        String address = "22 rue pasteur";
        when(fireStationRepository.findFireStationByAddress(address)).thenReturn(null);
        assertNull(fireStationService.findFireStationByAddress(address));
    }

    @Test
    public void findPersonsByStationNumberTest() throws ParseException {
        PersonCoveredByFireStationDto personCoveredByFireStation = new PersonCoveredByFireStationDto();
        FireStation fireStation = new FireStation("1509 Culver St","3");
        String numberStation = fireStation.getStation();
        when(fireStationRepository.findPersonsByStationNumber(numberStation)).thenReturn(personCoveredByFireStation);
        PersonCoveredByFireStationDto personCoveredByFireStationResult = fireStationService.findPersonsByStationNumber(numberStation);
        assertEquals(personCoveredByFireStationResult,personCoveredByFireStation);
    }

    @Test
    public void findPersonsByStationNumberTest_withNoExistingNumberStation() throws ParseException {
        String numberStation = "100";
        when(fireStationRepository.findPersonsByStationNumber(numberStation)).thenReturn(null);
        assertNull(fireStationService.findPersonsByStationNumber(numberStation));
    }
    @Test
    public void findAllPersonByStationTest() throws ParseException {
        Map<String, Set<PersonWithMedicalRecord>> map = new HashMap<>();
        FireStation fireStation1 = new FireStation("1509 Culver St","3");
        FireStation fireStation2 = new FireStation("29 15th St","2");
        FireStation fireStation3 = new FireStation("947 E. Rose Dr","1");
        String numberStation1 = fireStation1.getStation();
        String numberStation2 = fireStation2.getStation();
        String numberStation3 = fireStation3.getStation();
        List<String> stationNumbersList = new ArrayList<>();
        stationNumbersList.add(numberStation1);
        stationNumbersList.add(numberStation2);
        stationNumbersList.add(numberStation3);
        when(fireStationRepository.findAllPersonByStation(stationNumbersList)).thenReturn(map);
        Map<String , Set<PersonWithMedicalRecord>> mapResult = fireStationService.findAllPersonByStation(stationNumbersList);
        assertEquals(mapResult,map);
    }

    @Test
    public void findAllPersonByStationTest_withNoExistionNumberStations() throws ParseException {
        List<String > numberStations = new ArrayList<>();
        numberStations.add("100");
        numberStations.add("105");
        numberStations.add("120");
        when(fireStationRepository.findAllPersonByStation(numberStations)).thenReturn(null);
        assertNull(fireStationService.findAllPersonByStation(numberStations));
    }
}

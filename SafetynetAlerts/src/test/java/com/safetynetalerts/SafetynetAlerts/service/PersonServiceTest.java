package com.safetynetalerts.SafetynetAlerts.service;

import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonInfoDto;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import com.safetynetalerts.SafetynetAlerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    PersonRepository personRepository;
    @InjectMocks
    PersonService personService;
    List<Person> personList;

    @BeforeEach
    public void setUpTest(){
        personList = new ArrayList<>();
    }

    @Test
    public void getAllPersonsTest(){
        Person p1 = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        Person p2 = new Person("Khaoula","Hilali","22 rue pasteur","Paris","75000","234-879-0765","khilali@email.com");
        personList.add(p1);
        personList.add(p2);
        when(personRepository.getAllPersons()).thenReturn(personList);
        List<Person> personListResult = personService.getAllPersons();
        assertEquals(personListResult,personList);
    }

    @Test
    public void findPersonByNameTest(){
        Person person = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        String firstname = person.getFirstName();
        String lastname = person.getLastName();
        when(personRepository.findPersonByName(firstname,lastname)).thenReturn(person);
        Person personResult = personService.findPersonByName(firstname,lastname);
        assertEquals(personResult,person);
    }

    @Test
    public void findPersonByNameTest_NameDoesNotExist(){
        String firstname = "Khaoula";
        String lastname = "Hilali";
        when(personRepository.findPersonByName(firstname,lastname)).thenReturn(null);
        assertNull(personService.findPersonByName(firstname,lastname));
    }

    @Test
    public void findPersonByAddressTest(){
        Person person = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        personList.add(person);
        String address = person.getAddress();
        when(personRepository.findPersonByAddress(address)).thenReturn(personList);
        List<Person> personListResult = personService.findPersonByAddress(address);
        assertEquals(personListResult,personList);
    }

    @Test
    public void findPersonByAddressTest_AddressDoesNotExist(){
        String address = "22 rue pasteur";
        when(personRepository.findPersonByAddress(address)).thenReturn(null);
        assertNull(personService.findPersonByAddress(address));
    }

    @Test
    public void createPersonTest(){
        Person person = new Person("Khaoula","Hilali","22 rue pasteur","Paris","75000","234-879-0765","khilali@email.com");
        personList.add(person);
        when(personRepository.addPerson(person)).thenReturn(personList);
        List<Person> personListResult = personService.createPerson(person);
        assertEquals(personListResult,personList);
    }

    @Test
    public void createPersonTest_withExistingPerson(){
        Person person = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        when(personRepository.addPerson(person)).thenReturn(null);
        assertNull(personService.createPerson(person));
    }

    @Test
    public void updatePersonTest(){
        Person person = new Person("John","Boyd","1509 Culver Street","Culver","97451","841-874-6512","jaboyd@email.com");
        when(personRepository.savePerson(person)).thenReturn(person);
        Person personResult = personService.updatePerson(person);
        assertEquals(personResult,person);
    }

    @Test
    public void updatePersonTest_withNoExistingPerson(){
        Person person = new Person("Khaoula","Hilali","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        when(personRepository.savePerson(person)).thenReturn(null);
        assertNull(personService.updatePerson(person));
    }

    @Test //A revoir avec Reda
    public void deletePersonTest(){
        Person p1 = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        Person p2 = new Person("Khaoula","Hilali","22 rue pasteur","Paris","75000","234-879-0765","khilali@email.com");
        personList.add(p1);
        personList.add(p2);
        String firstname = p1.getFirstName();
        String lastname = p1.getLastName();
        when(personRepository.deletePerson(firstname,lastname)).thenReturn(personList);
        System.out.println(personList);
        List<Person> personListResult = personService.deletePerson(firstname,lastname);
        assertEquals(personListResult, personList);
    }

    @Test
    public void deletePersonTest_withNoExistingPerson(){
        String firstname = "Khaoula";
        String lastname = "Hilali";
        when(personRepository.deletePerson(firstname,lastname)).thenReturn(null);
        assertNull(personService.deletePerson(firstname,lastname));
    }

    @Test
    public void getAllEmailsByCityTest(){
        List<String> emails = new ArrayList<>();
        Person person = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        String city = person.getCity();
        when(personRepository.getAllEmailsByCity(city)).thenReturn(emails);
        List<String> emailsResult = personService.getAllEmailsByCity(city);
        assertEquals(emailsResult,emails);
    }

    @Test
    public void getPersonInfoTest() throws ParseException {
        PersonInfoDto personInfoDto = new PersonInfoDto();
        Person person = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        String firstname = person.getFirstName();
        String lastname = person.getLastName();
        when(personRepository.getPersonInfo(firstname,lastname)).thenReturn(personInfoDto);
        PersonInfoDto personInfoDtoResult = personService.getPersonInfo(firstname,lastname);
        assertEquals(personInfoDtoResult,personInfoDto);
    }

    @Test
    public void getPersonInfoTest_withNoExistingName() throws ParseException {
        String firstname = "Khaoula";
        String lastname = "Hilali";
        when(personRepository.getPersonInfo(firstname,lastname)).thenReturn(null);
        assertNull(personService.getPersonInfo(firstname,lastname));
    }

}

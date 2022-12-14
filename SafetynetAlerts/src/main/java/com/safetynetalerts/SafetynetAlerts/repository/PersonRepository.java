package com.safetynetalerts.SafetynetAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.SafetynetAlerts.database.DataStore;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonInfoDto;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import com.safetynetalerts.SafetynetAlerts.service.MedicalRecordService;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    DataStore dataStore;
    MedicalRecordRepository medicalRecordRepository;

    public PersonRepository(DataStore dataStore, MedicalRecordRepository medicalRecordRepository) {
        this.dataStore = dataStore;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    // This method return the list of persons in the Json file
    public List<Person> getAllPersons(){
        return dataStore.getPersons();
    }

    // This method return a person by given firstname and lastname
    public Person findPersonByName(String firstName, String lastName){
        List<Person> personList = this.getAllPersons();
        for (Person person : personList){
            if ((person.getFirstName().equals(firstName)) && (person.getLastName().equals(lastName))){
                return person;
            }
        }return null;
    }

    public List<Person> findPersonByAddress(String address){
        List<Person> personList = this.getAllPersons();
        List<Person>personByAddress = new ArrayList<>();
        for (Person person: personList){
            if (person.getAddress().equals(address)){
                personByAddress.add(person);
            }
        }return personByAddress;
    }

    // This method allows to add a new person to the list of persons
    public List<Person> addPerson(Person person){
        List<Person> personList = this.getAllPersons();
        personList.add(person);
        return personList;
    }

    // This method allows to update the informations of a person
    public Person savePerson(Person person){
        String address = person.getAddress();
        person.setAddress(address);

        String city = person.getCity();
        person.setCity(city);

        String zip = person.getZip();
        person.setZip(zip);

        String phone = person.getPhone();
        person.setPhone(phone);

        String email = person.getEmail();
        person.setEmail(email);

        if ((person.getAddress().equals(address)) && (person.getCity().equals(city)) && (person.getZip().equals(zip)) && (person.getPhone().equals(phone)) && (person.getEmail().equals(email))){
            return null;
        }else {
            return person;
        }
    }

    // This method allows to delete a person from a list of persons
    public List<Person> deletePerson (String firstName, String lastname){
        List<Person> personList = this.getAllPersons();
        for (Person person : personList){
            if ((person.getFirstName().equals(firstName)) && (person.getLastName().equals(lastname))){
                personList.remove(person);
            }
        }return personList;
    }

    //localhost:8080/communityemail?city=<city>
    // This method return a list of emails of all the person who living in a city
    public List<String> getAllEmailsByCity(String city){
        List<String> emails = new ArrayList<>();
        for (Person person: this.getAllPersons()){
            if (person.getCity().equals(city)){
                emails.add(person.getEmail());
            }
        }return emails;
    }

    //localhost:8080/personInfo?firstName=<firstname<&lastName=<lastName>
    // This method return all the information related to a person from the list of persons by given his name
    public PersonInfoDto getPersonInfo(String firstName, String lastName) throws ParseException {
        PersonInfoDto personInfoDto = new PersonInfoDto();
        Person person = this.findPersonByName(firstName,lastName);
        MedicalRecord medicalRecord = medicalRecordRepository.findMedicalRecordByName(firstName,lastName);
        personInfoDto.setFirstName(firstName);
        personInfoDto.setLastName(lastName);
        personInfoDto.setAddress(person.getAddress());
        personInfoDto.setAge(medicalRecord.getAge());
        personInfoDto.setEmail(person.getEmail());
        personInfoDto.setMedications(medicalRecord.getMedications());
        personInfoDto.setAllergies(medicalRecord.getAllergies());
        return personInfoDto;
    }
}

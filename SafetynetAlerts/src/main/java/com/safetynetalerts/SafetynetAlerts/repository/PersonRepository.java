package com.safetynetalerts.SafetynetAlerts.repository;

import com.safetynetalerts.SafetynetAlerts.database.DataStore;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonInfoDto;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import org.springframework.stereotype.Repository;

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
        for (Person person1 : personList){
            if (person1.getFirstName().equals(person.getFirstName()) && person1.getLastName().equals(person.getLastName()))
                break;
            else
                personList.add(person);
                break;

        }
        return personList;

    }

    // This method allows to update the informations of a person
    public Person savePerson(Person person){
        if (person.getFirstName() != null && person.getLastName() != null){
            for (Person person1: this.getAllPersons())
                if (person1.getFirstName().equals(person.getFirstName()) && person1.getLastName().equals(person.getLastName())){
                    person1.setAddress(person.getAddress());
                    person1.setCity(person.getCity());
                    person1.setZip(person.getZip());
                    person1.setPhone(person.getPhone());
                    person1.setEmail(person.getEmail());
                    return person;
                }
        }
        return null;
    }

    // This method allows to delete a person from a list of persons
    public List<Person> deletePerson (String firstName, String lastname){
        List<Person> personList = this.getAllPersons();
        for (Person person : personList){
            if ((person.getFirstName().equals(firstName)) && (person.getLastName().equals(lastname))){
                personList.remove(person);
                break;
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
        if (person == null || medicalRecord == null) return null;
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

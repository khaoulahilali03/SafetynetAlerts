package com.safetynetalerts.SafetynetAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.SafetynetAlerts.database.DataStore;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class PersonRepository {
    String fileName = "D:/OPC/Formation_Java/P5/SafetynetAlerts/SafetynetAlerts/src/main/resources/data.json";
    ObjectMapper mapper = new ObjectMapper();
    DataStore store = mapper.readValue(Paths.get(fileName).toFile(), DataStore.class);
    List<Person> personList = store.getPersons();

    public PersonRepository() throws IOException {
    }

    // This method return the list of persons in the Json file
    public List<Person> findAll(){return personList;}

    public Person findByName(String firstName, String lastName){
        for (Person person : personList){
            if ((person.getFirstName().equals(firstName)) && (person.getLastName().equals(lastName))){
                return person;
            }
        }return null;
    }

    // This method allows to add a new person to the list of persons
    public List<Person> addPerson(Person person){
        personList.add(person);
        return personList;
    }

    // This method allows to update the informations of a person
    public Person updatePerson(Person person){
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
        for (Person person : personList){
            if ((person.getFirstName().equals(firstName)) && (person.getLastName().equals(lastname))){
                personList.remove(person);
            }
        }return personList;
    }

}

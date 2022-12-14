package com.safetynetalerts.SafetynetAlerts.service;

import com.safetynetalerts.SafetynetAlerts.model.Person;
import com.safetynetalerts.SafetynetAlerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons(){

        return personRepository.getAllPersons();
    }

    public Person findPersonByName(String firstName, String lastName){
        return personRepository.findPersonByName(firstName,lastName);
    }

    public List<Person> findPersonByAddress(String address){
        return personRepository.findPersonByAddress(address);
    }

    public List<Person> createPerson(Person person){

        return personRepository.addPerson(person);
    }

    public Person updatePerson(Person person){

        return personRepository.savePerson(person);
    }

    public List<Person> deletePerson(String firstName, String lastName){
        return personRepository.deletePerson(firstName,lastName);
    }

    public List<String> getAllEmailsByCity(String city) {
        return personRepository.getAllEmailsByCity(city);
    }
}

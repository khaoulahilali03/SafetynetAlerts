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

    public PersonService() {
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findByName(String firstName, String lastName){
        return personRepository.findByName(firstName,lastName);
    }

    public List<Person> addPerson(Person person){
        return personRepository.addPerson(person);
    }

    public Person updatePerson(Person person){
        return personRepository.updatePerson(person);
    }

    public List<Person> deletePerson(String firstName, String lastName){
        return personRepository.deletePerson(firstName,lastName);
    }
}

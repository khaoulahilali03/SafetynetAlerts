package com.safetynetalerts.SafetynetAlerts.controller;


import com.safetynetalerts.SafetynetAlerts.model.Person;
import com.safetynetalerts.SafetynetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("person")
    public List<Person> getAllPersons(){
        return personService.getAllPersons();
    }

    @PostMapping("person")
    public List<Person> createPerson(@RequestBody Person person){
        return personService.createPerson(person);
    }

    @PutMapping("person/{firstname}/{lastname}")
    public List<Person> updatePerson(@PathVariable("firstname") final String firstName, @PathVariable("lastname") final String lastName, @RequestBody Person person){
        Person currentPerson = personService.findPersonByName(firstName,lastName);
        if (currentPerson != null){

            String address = person.getAddress();
            if (address != null){
                currentPerson.setAddress(address);
            }

            String city = person.getCity();
            if (city != null){
                currentPerson.setCity(city);
            }

            String zip = person.getZip();
            if (zip != null){
                currentPerson.setZip(zip);
            }

            String phone = person.getPhone();
            if (phone != null){
                currentPerson.setPhone(phone);
            }

            String email = person.getEmail();
            if (email != null){
                currentPerson.setEmail(email);
            }

            personService.updatePerson(currentPerson);
            return personService.createPerson(currentPerson);
        }else {
            return null;
        }
    }

    @DeleteMapping("person/{firstname}/{lastname}")
    public List<Person> deletePerson(@PathVariable ("firstname") String firstName, @PathVariable("lastname") String lastName){
        return personService.deletePerson(firstName,lastName);
    }
}

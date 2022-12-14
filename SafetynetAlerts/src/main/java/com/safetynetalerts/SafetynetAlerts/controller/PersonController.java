package com.safetynetalerts.SafetynetAlerts.controller;


import com.safetynetalerts.SafetynetAlerts.model.DTO.EmptyJsonResponse;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonInfoDto;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import com.safetynetalerts.SafetynetAlerts.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
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
    public ResponseEntity<Object> updatePerson(@PathVariable("firstname") final String firstName, @PathVariable("lastname") final String lastName, @RequestBody Person person){
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
            return new ResponseEntity<>(personService.createPerson(currentPerson), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new EmptyJsonResponse(),HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("person/{firstname}/{lastname}")
    public List<Person> deletePerson(@PathVariable ("firstname") String firstName, @PathVariable("lastname") String lastName){
        return personService.deletePerson(firstName,lastName);
    }

    //localhost:8080/communityemail?city=<city>
    @GetMapping("communityemail")
    public List<String> getAllEmailsByCity(@RequestParam ("city")String city) {
        List<String> emails = personService.getAllEmailsByCity(city);
        return emails;
    }

    //localhost:8080/personInfo?firstName=<firstname<&lastName=<lastName>
    @GetMapping("personinfo")
    public ResponseEntity<Object> getPersonInfo(@RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName) throws ParseException {
        PersonInfoDto personInfo = personService.getPersonInfo(firstName,lastName);
        if (personInfo != null)
            return new ResponseEntity<>(personInfo, HttpStatus.OK);
        else
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);

        }


}

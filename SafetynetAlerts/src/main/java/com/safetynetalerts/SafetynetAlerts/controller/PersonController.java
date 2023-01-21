package com.safetynetalerts.SafetynetAlerts.controller;

import com.safetynetalerts.SafetynetAlerts.model.DTO.EmptyJsonResponse;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonInfoDto;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import com.safetynetalerts.SafetynetAlerts.service.PersonService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
public class PersonController {

    private PersonService personService;
    private static final Logger logger = LogManager.getLogger("PersonController");


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("person")
    public List<Person> getAllPersons(){
        return personService.getAllPersons();
    }

    @PostMapping("person")
    public List<Person> createPerson(@RequestBody Person person){
        List<Person> persons = personService.createPerson(person);
        logger.info(""+person+" is created !");
        return persons;
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
            logger.info(""+person+ " is updated !");
            return new ResponseEntity<>(personService.createPerson(currentPerson), HttpStatus.OK);
        }else {
            logger.error("Failed to update :"+person);
            return new ResponseEntity<>(new EmptyJsonResponse(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("person/{firstname}/{lastname}")
    public List<Person> deletePerson(@PathVariable ("firstname") String firstName, @PathVariable("lastname") String lastName){
        logger.info("Person " +firstName +" "+ lastName + " is deleted !");
        return personService.deletePerson(firstName,lastName);
    }

    //localhost:8080/communityemail?city=<city>
    @GetMapping("communityEmail")
    public List<String> getAllEmailsByCity(@RequestParam ("city")String city) {
        List<String> emails = personService.getAllEmailsByCity(city);
        if (emails != null){
            logger.info("City " +city+ " is quiered to get all emails.");
            return emails;
        }else {
            logger.error("Failed to get all emails by city: " +city);
            return null;
        }
    }

    //localhost:8080/personInfo?firstName=<firstname<&lastName=<lastName>
    @GetMapping("personInfo")
    public ResponseEntity<Object> getPersonInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws ParseException {
        PersonInfoDto personInfo = personService.getPersonInfo(firstName,lastName);
        if (personInfo != null)
        {logger.info("" +firstName+" "+lastName+ "information");
            return new ResponseEntity<>(personInfo, HttpStatus.OK);}
        else
            {logger.error("Person not found");
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);}

        }


}

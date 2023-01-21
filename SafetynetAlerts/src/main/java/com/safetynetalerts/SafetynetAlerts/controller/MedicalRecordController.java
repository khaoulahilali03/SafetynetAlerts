package com.safetynetalerts.SafetynetAlerts.controller;

import com.safetynetalerts.SafetynetAlerts.model.DTO.EmptyJsonResponse;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
public class MedicalRecordController {
    private MedicalRecordService medicalRecordService;

    private static final Logger logger = LogManager.getLogger("MedicalRecordController");
    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("medicalRecord")
    public List<MedicalRecord> getAllMedicalRecords(){
        return medicalRecordService.getAllMedicalRecords();
    }

    @PostMapping("medicalRecord")
    public List<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info(""+medicalRecord+"is created");
        return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PutMapping("medicalRecord/{firstname}/{lastname}")
    public ResponseEntity<Object> updateMedicalRecord(@PathVariable("firstname") String firstName, @PathVariable("lastname") String lastName, @RequestBody MedicalRecord medicalRecord){
        MedicalRecord currentMedicalRecord = medicalRecordService.findMedicalRecordByName(firstName,lastName);
        if (currentMedicalRecord != null){

            String birthdate = medicalRecord.getBirthdate();
            if (birthdate != null){
                currentMedicalRecord.setBirthdate(birthdate);
            }

            List<String> medications = medicalRecord.getMedications();
            if (medications != null){
                currentMedicalRecord.setMedications(medications);
            }

            List<String> allergies = medicalRecord.getAllergies();
            if (allergies != null){
                currentMedicalRecord.setAllergies(allergies);
            }
            medicalRecordService.updateMedicalRecord(currentMedicalRecord);
            logger.info(""+medicalRecord+"is updated");
            return new ResponseEntity<>(medicalRecordService.createMedicalRecord(currentMedicalRecord), HttpStatus.OK);
        }else {
            logger.error("Failed to update "+medicalRecord);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("medicalRecord/{firstname}/{lastname}")
    public List<MedicalRecord> deleteMedicalRecord(@PathVariable("firstname") String firstName,@PathVariable("lastname") String lastName){
        logger.info("Medical Record " +firstName+" "+lastName+"is deleted");
        return  medicalRecordService.deleteMedicalRecord(firstName,lastName);
    }

    //localhost:8080/childAlert?address=<address>
    @GetMapping("childAlert")
    public ResponseEntity<Object> getChildAlert(@RequestParam("address") String address) throws ParseException {
        if (address != null)
        {logger.info("Children living in the"+address+"");
            return new ResponseEntity<>(medicalRecordService.getChildAlert(address),HttpStatus.OK) ;}
        else
        {logger.error("This address is not found");
            return new ResponseEntity<>(new EmptyJsonResponse(),HttpStatus.INTERNAL_SERVER_ERROR);}
    }
}

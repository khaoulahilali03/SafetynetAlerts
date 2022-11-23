package com.safetynetalerts.SafetynetAlerts.controller;

import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.repository.MedicalRecordRepository;
import com.safetynetalerts.SafetynetAlerts.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {
    private MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("medicalrecord")
    public List<MedicalRecord> findAll(){
        return medicalRecordService.findAll();
    }

    @PostMapping("medicalrecord")
    public List<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        return medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @PutMapping("medicalrecord/{firstname}/{lastname}")
    public List<MedicalRecord> updateMedicalRecord(@PathVariable("firstname") String firstName, @PathVariable("lastname") String lastName, @RequestBody MedicalRecord medicalRecord){
        MedicalRecord currentMedicalRecord = medicalRecordService.findByName(firstName,lastName);
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
            return medicalRecordService.addMedicalRecord(currentMedicalRecord);
        }else {
            return null;
        }
    }

    @DeleteMapping("medicalrecord/{firstname}/{lastname}")
    public List<MedicalRecord> deleteMedicalRecord(@PathVariable("firstname") String firstName,@PathVariable("lastname") String lastName){
        return  medicalRecordService.deleteMedicalRecord(firstName,lastName);

    }

}

package com.safetynetalerts.SafetynetAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.SafetynetAlerts.database.DataStore;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
@Repository
public class MedicalRecordRepository {

    DataStore dataStore;
    PersonRepository personRepository;

    @Autowired
    @Lazy
    public MedicalRecordRepository(DataStore dataStore, PersonRepository personRepository) {
        this.dataStore = dataStore;
        this.personRepository = personRepository;
    }

    // This method return the list of medicalrecord in the Json file
    public List<MedicalRecord> getAllMedicalRecords(){
        return dataStore.getMedicalrecords();
    }

    // This method return a medicalrecord by given firstname and lastname
    public MedicalRecord findMedicalRecordByName(String firstName, String lastName){
        List<MedicalRecord> medicalRecordList = this.getAllMedicalRecords();
        for (MedicalRecord medicalRecord : medicalRecordList){
            if ((medicalRecord.getFirstName().equals(firstName)) && (medicalRecord.getLastName().equals(lastName))){
                return medicalRecord;
            }
        }return null;
    }
    // This method allows to add a new medicalrecord to the list of persons
    public List<MedicalRecord> addMedicalRecord(MedicalRecord medicalRecord){
        List<MedicalRecord> medicalRecordList= this.getAllMedicalRecords();
        medicalRecordList.add(medicalRecord);
        return medicalRecordList;
    }

    // This method allows to update the informations of a medicalrecord
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord){
        String birthdate = medicalRecord.getBirthdate();
        medicalRecord.setBirthdate(birthdate);

        List<String> medications = medicalRecord.getMedications();
        medicalRecord.setMedications(medications);

        List<String> allergies = medicalRecord.getAllergies();
        medicalRecord.setAllergies(allergies);

        if ((medicalRecord.getBirthdate().equals(birthdate)) && (medicalRecord.getMedications().equals(medications)) && (medicalRecord.getAllergies().equals(allergies))){
            return null;
        }else {
            return medicalRecord;
        }
    }

    // This method allows to delete a medicalrecord from a list of medicalrecord
    public List<MedicalRecord> deleteMedicalRecord(String firstName, String lastName){
        List<MedicalRecord> medicalRecordList = this.getAllMedicalRecords();
        for (MedicalRecord medicalRecord : medicalRecordList){
            if ((medicalRecord.getFirstName().equals(firstName)) && (medicalRecord.getLastName().equals(lastName))){
                medicalRecordList.remove(medicalRecord);
            }
        }return medicalRecordList;
    }
}

package com.safetynetalerts.SafetynetAlerts.service;

import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }
    public List<MedicalRecord> getAllMedicalRecords(){
        return medicalRecordRepository.getAllMedicalRecords();
    }

    public MedicalRecord findMedicalRecordByName(String firstName, String lastName){
        return medicalRecordRepository.findMedicalRecordByName(firstName,lastName);
    }

    public List<MedicalRecord> createMedicalRecord(MedicalRecord medicalRecord){
        return medicalRecordRepository.addMedicalRecord(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord){
        return medicalRecordRepository.saveMedicalRecord(medicalRecord);
    }

    public List<MedicalRecord> deleteMedicalRecord(String firstName, String lastName){
        return medicalRecordRepository.deleteMedicalRecord(firstName,lastName);
    }
}

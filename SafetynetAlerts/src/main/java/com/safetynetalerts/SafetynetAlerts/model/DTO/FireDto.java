package com.safetynetalerts.SafetynetAlerts.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class FireDto {

    private List<PersonWithMedicalRecord> personWithMedicalRecordList;
    private List<String> stationsNumbers;

    public FireDto() {
    }

    public FireDto(List<PersonWithMedicalRecord> personWithMedicalRecordDtoList, List<String> stationsNumbers) {
        this.personWithMedicalRecordList = new ArrayList<>(personWithMedicalRecordDtoList);
        this.stationsNumbers = new ArrayList<>(stationsNumbers);
    }

    public List<PersonWithMedicalRecord> getPersonWithMedicalRecordDtoList() {
        return new ArrayList<>(personWithMedicalRecordList);
    }

    public void setPersonWithMedicalRecordDtoList(List<PersonWithMedicalRecord> personWithMedicalRecordDtoList) {
        this.personWithMedicalRecordList = new ArrayList<>(personWithMedicalRecordDtoList);
    }

    public List<String> getStationsNumbers() {
        return new ArrayList<>(stationsNumbers);
    }

    public void setStationsNumbers(List<String> stationsNumbers) {
        this.stationsNumbers = new ArrayList<>(stationsNumbers);
    }
}

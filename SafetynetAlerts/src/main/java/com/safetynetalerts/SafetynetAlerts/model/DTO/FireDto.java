package com.safetynetalerts.SafetynetAlerts.model.DTO;

import java.util.List;

public class FireDto {

    private List<PersonWithMedicalRecord> personWithMedicalRecordList;
    private List<String> stationsNumbers;

    public FireDto() {
    }

    public FireDto(List<PersonWithMedicalRecord> personWithMedicalRecordDtoList, List<String> stationsNumbers) {
        this.personWithMedicalRecordList = personWithMedicalRecordDtoList;
        this.stationsNumbers = stationsNumbers;
    }

    public List<PersonWithMedicalRecord> getPersonWithMedicalRecordDtoList() {
        return personWithMedicalRecordList;
    }

    public void setPersonWithMedicalRecordDtoList(List<PersonWithMedicalRecord> personWithMedicalRecordDtoList) {
        this.personWithMedicalRecordList = personWithMedicalRecordDtoList;
    }

    public List<String> getStationsNumbers() {
        return stationsNumbers;
    }

    public void setStationsNumbers(List<String> stationsNumbers) {
        this.stationsNumbers = stationsNumbers;
    }
}

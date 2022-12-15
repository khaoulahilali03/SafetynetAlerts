package com.safetynetalerts.SafetynetAlerts.model.DTO;

public class FloodStationDto {

    private String address;
    private PersonWithMedicalRecord personWithMedicalRecord;

    public FloodStationDto() {
    }

    public FloodStationDto(String address, PersonWithMedicalRecord personWithMedicalRecord) {
        this.address = address;
        this.personWithMedicalRecord = personWithMedicalRecord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PersonWithMedicalRecord getPersonWithMedicalRecord() {
        return personWithMedicalRecord;
    }

    public void setPersonWithMedicalRecord(PersonWithMedicalRecord personWithMedicalRecord) {
        this.personWithMedicalRecord = personWithMedicalRecord;
    }

}

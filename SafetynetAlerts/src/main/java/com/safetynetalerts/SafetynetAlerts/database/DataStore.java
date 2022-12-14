package com.safetynetalerts.SafetynetAlerts.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Component
public class DataStore {

    private List<Person> persons= new ArrayList<>();
    private List<FireStation> firestations = new ArrayList<>();
    private List<MedicalRecord> medicalrecords = new ArrayList<>();

    public DataStore() {
        super();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<FireStation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<FireStation> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    @PostConstruct
    public DataStore readObjectFromJsonFile() throws IOException {
        String fileName = "D:/OPC/Formation_Java/P5/SafetynetAlerts/SafetynetAlerts/src/main/resources/data.json";
        ObjectMapper mapper = new ObjectMapper();
        DataStore store = mapper.readValue(Paths.get(fileName).toFile(), DataStore.class);
        persons = store.getPersons();
        firestations = store.getFirestations();
        medicalrecords = store.getMedicalrecords();
        return store;
    }

}

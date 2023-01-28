package com.safetynetalerts.SafetynetAlerts.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Component
public class DataStore {

    @Value("classpath:data.json")
    Resource resource;

    private List<Person> persons= new ArrayList<>();
    private List<FireStation> firestations = new ArrayList<>();
    private List<MedicalRecord> medicalrecords = new ArrayList<>();

    public DataStore() {
        super();
    }

    public List<Person> getPersons() {
        return new ArrayList<>(persons);
    }

    public void setPersons(List<Person> persons) {
        this.persons = new ArrayList<>(persons);
    }

    public List<FireStation> getFirestations() {
        return new ArrayList<>(firestations);
    }

    public void setFirestations(List<FireStation> firestations) {
        this.firestations = new ArrayList<>(firestations);
    }

    public List<MedicalRecord> getMedicalrecords() {
        return new ArrayList<>(medicalrecords);
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = new ArrayList<>(medicalrecords);
    }

    @PostConstruct
    public DataStore readObjectFromJsonFile() throws IOException {
        File file = resource.getFile();
        ObjectMapper mapper = new ObjectMapper();
        DataStore store = mapper.readValue(file,DataStore.class);
        persons = store.getPersons();
        firestations = store.getFirestations();
        medicalrecords = store.getMedicalrecords();
        return store;
    }

}

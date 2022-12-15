package com.safetynetalerts.SafetynetAlerts.repository;

import com.safetynetalerts.SafetynetAlerts.database.DataStore;
import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
public class FireStationRepository {
    @Autowired
    DataStore dataStore;
    PersonRepository personRepository;
    MedicalRecordRepository medicalRecordRepository;

    public FireStationRepository(DataStore dataStore, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository) {
        this.dataStore = dataStore;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    // This method return the list of firestation in the Json file
    public List<FireStation> getAllFireStations(){
        return dataStore.getFirestations();
    }

    // This method return a firestation by given address
    public FireStation findFireStationByAddress(String address){
        List<FireStation> fireStationList = this.getAllFireStations();
        for (FireStation fireStation : fireStationList){
            if (fireStation.getAddress().equals(address)){
                return fireStation;
            }
        }return null;
    }

    // This method returna firestation by a given station number
    public FireStation findFirestationByNumberStation(String numberStation){
        List<FireStation> fireStationList = this.getAllFireStations();
        for (FireStation fireStation : fireStationList){
            if (fireStation.getStation().equals(numberStation))
                return fireStation;
        }return null;
    }

    // This method return a set of all the addresses of the firestation in the json path
    public Set<String> findAllAddresses(Set<FireStation> stationsNumber){
        Set<String> addresses = new HashSet<>();
        for (FireStation fireStation : this.getAllFireStations()){
            addresses.add(fireStation.getAddress());
        }return addresses;
    }

    //This method allows to add a new firestation to the list of firestations
    public List<FireStation> addFireStation(FireStation fireStation){
        List<FireStation> fireStationList = this.getAllFireStations();
        fireStationList.add(fireStation);
        return fireStationList;
    }

    // This method allows to update the informations of a fireStation
    public FireStation saveFireStation (FireStation fireStation){
        String station = fireStation.getStation();
        fireStation.setStation(station);
        if (fireStation.getStation().equals(station)){
            return null;
        }else {
            return fireStation;
        }
    }

    // This method allows to delete a firestation from a list of firestation
    public List<FireStation> deleteFireStation(String address){
        List<FireStation> fireStationList = this.getAllFireStations();
        for (FireStation fireStation : fireStationList){
            if (fireStation.getAddress().equals(address)){
                fireStationList.remove(fireStation);
            }
        }return fireStationList;
    }

    //localhost:8080/phoneAlert?firestation=<firestation_number>
    // This method return the list of phone number of the person related to a specific number station 
    public LinkedHashSet<String> getPhoneNumberForAStation(String numberStation){
        LinkedHashSet<String> phoneNumbers = new LinkedHashSet<>();
        for (FireStation fireStation: this.getAllFireStations()){
            if (fireStation.getStation().equals(numberStation)){
                for (Person person: personRepository.getAllPersons()){
                    if (fireStation.getAddress().equals(person.getAddress())){
                        phoneNumbers.add(person.getPhone());
                    }
                }
            }
        }return phoneNumbers;
    }



}

package com.safetynetalerts.SafetynetAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.SafetynetAlerts.database.DataStore;
import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class FireStationRepository {
    String fileName = "D:/OPC/Formation_Java/P5/SafetynetAlerts/SafetynetAlerts/src/main/resources/data.json";
    ObjectMapper mapper = new ObjectMapper();
    DataStore store = mapper.readValue(Paths.get(fileName).toFile(), DataStore.class);
    List<FireStation> fireStationList = store.getFirestations();

    public FireStationRepository() throws IOException {
    }
    // This method return the list of firestation in the Json file
    public List<FireStation> findAll(){
        return fireStationList;
    }

    // This method return a firestation by given her address
    public FireStation findByAddress(String address){
        for (FireStation fireStation : fireStationList){
            if (fireStation.getAddress().equals(address)){
                return fireStation;
            }
        }return null;
    }

    //This method allows to add a new firestation to the list of firestations
    public List<FireStation> addFireStation(FireStation fireStation){
        fireStationList.add(fireStation);
        return fireStationList;
    }

    // This method allows to update the informations of a fireStation
    public FireStation updateFireStation (FireStation fireStation){
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
        for (FireStation fireStation : fireStationList){
            if (fireStation.getAddress().equals(address)){
                fireStationList.remove(fireStation);
            }
        }return fireStationList;
    }

}

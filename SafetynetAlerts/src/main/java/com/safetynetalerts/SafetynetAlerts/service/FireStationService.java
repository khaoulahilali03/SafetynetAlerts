package com.safetynetalerts.SafetynetAlerts.service;

import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {
    @Autowired
    FireStationRepository fireStationRepository;

    public FireStationService() {
    }

    public FireStationService(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    public List<FireStation> findAll(){
        return fireStationRepository.findAll();
    }

    public FireStation findByAddress(String address){
        return fireStationRepository.findByAddress(address);
    }

    public List<FireStation> addFireStation(FireStation fireStation){
        return fireStationRepository.addFireStation(fireStation);
    }

    public FireStation updateFireStation(FireStation fireStation){
        return fireStationRepository.updateFireStation(fireStation);
    }

    public List<FireStation> deleteFireStation(String address){
        return fireStationRepository.deleteFireStation(address);
    }
}

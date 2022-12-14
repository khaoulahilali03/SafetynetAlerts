package com.safetynetalerts.SafetynetAlerts.service;

import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {

    FireStationRepository fireStationRepository;

    public FireStationService(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    public List<FireStation> getAllFireStations() {
        return fireStationRepository.getAllFireStations();
    }

    public FireStation findFireStationByAddress(String address) {
        return fireStationRepository.findFireStationByAddress(address);
    }

    public List<FireStation> createFireStation(FireStation fireStation) {
        return fireStationRepository.addFireStation(fireStation);
    }

    public FireStation updateFireStation(FireStation fireStation) {
        return fireStationRepository.saveFireStation(fireStation);
    }

    public List<FireStation> deleteFireStation(String address) {
        return fireStationRepository.deleteFireStation(address);
    }


}

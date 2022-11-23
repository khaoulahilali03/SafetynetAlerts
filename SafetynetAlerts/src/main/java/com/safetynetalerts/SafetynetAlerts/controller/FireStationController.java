package com.safetynetalerts.SafetynetAlerts.controller;

import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.service.FireStationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationController {
    private FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("firestation")
    public List<FireStation> fireStationList(){
        return fireStationService.findAll();
    }

    @PostMapping("firestation")
    public List<FireStation> createFireStation(@RequestBody FireStation fireStation){
        return fireStationService.addFireStation(fireStation);
    }

    @PutMapping("firestation/{address}")
    public List<FireStation> updateFireStation(@PathVariable("address") String address, @RequestBody FireStation fireStation){
        FireStation currentFireStation = fireStationService.findByAddress(address);
        if (currentFireStation != null){
            String station = fireStation.getStation();
            if (station != null){
                currentFireStation.setStation(station);
            }
            fireStationService.updateFireStation(currentFireStation);
            return fireStationService.addFireStation(currentFireStation);
        }else {
            return null;
        }
    }

    @DeleteMapping("fireStation/{address}")
    public List<FireStation> deleteFireStation(@PathVariable ("address") String address){
        return fireStationService.deleteFireStation(address);
    }
}
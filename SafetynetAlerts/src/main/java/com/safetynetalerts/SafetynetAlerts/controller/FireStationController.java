package com.safetynetalerts.SafetynetAlerts.controller;

import com.safetynetalerts.SafetynetAlerts.model.DTO.FireDto;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonCoveredByFireStationDto;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonWithMedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.service.FireStationService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class FireStationController {
    private FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("firestation")
    public List<FireStation> getAllFireStations(){
        return fireStationService.getAllFireStations();
    }

    @PostMapping("firestation")
    public List<FireStation> createFireStation(@RequestBody FireStation fireStation){
        return fireStationService.createFireStation(fireStation);
    }

    @PutMapping("firestation/{address}")
    public List<FireStation> updateFireStation(@PathVariable("address") String address, @RequestBody FireStation fireStation){
        FireStation currentFireStation = fireStationService.findFireStationByAddress(address);
        if (currentFireStation != null){
            String station = fireStation.getStation();
            if (station != null){
                currentFireStation.setStation(station);
            }
            fireStationService.updateFireStation(currentFireStation);
            return fireStationService.createFireStation(currentFireStation);
        }else {
            return null;
        }
    }

    @DeleteMapping("fireStation/{address}")
    public List<FireStation> deleteFireStation(@PathVariable ("address") String address){
        return fireStationService.deleteFireStation(address);
    }

    //localhost:8080/phoneAlert?firestation=<firestation_number>
    @GetMapping("phonealert")
    public LinkedHashSet<String> getPhoneNumberForAStation(@RequestParam("numberStation") String numberStation){
        return fireStationService.getPhoneNumberForAStation(numberStation);
    }

    //localhost:8080/fire?address=<address>
    @GetMapping("fire")
    public FireDto findStationByAddress(@RequestParam("address") String address) throws ParseException {
        return fireStationService.findStationByAddress(address);
    }

    //localhost:8080/firestation?stationNumber=<station_number>
    @GetMapping("firestationsn")
    public PersonCoveredByFireStationDto findPersonsByStationNumber(@RequestParam("numberStation") String numberStation) throws ParseException {
        return fireStationService.findPersonsByStationNumber(numberStation);
    }


    //localhost:8080/flood:stations?stations=<a list of station_number>
    @GetMapping("flood")
    public Map<String, Set<PersonWithMedicalRecord>> findAllPersonByStation(@RequestParam("stationsNumber") List<String> stationsNumber) throws ParseException {
        return fireStationService.findAllPersonByStation(stationsNumber);
    }



}

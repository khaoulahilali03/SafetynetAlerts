package com.safetynetalerts.SafetynetAlerts.controller;

import com.safetynetalerts.SafetynetAlerts.model.DTO.EmptyJsonResponse;
import com.safetynetalerts.SafetynetAlerts.model.DTO.FireDto;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonCoveredByFireStationDto;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonWithMedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class FireStationController {
    private FireStationService fireStationService;

    private static final Logger logger = LogManager.getLogger("FireStationController");

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("firestations")
    public List<FireStation> getAllFireStations(){
        return fireStationService.getAllFireStations();
    }

    @PostMapping("firestations")
    public List<FireStation> createFireStation(@RequestBody FireStation fireStation){
        logger.info(""+fireStation+" is created !");
        return fireStationService.createFireStation(fireStation);
    }

    @PutMapping("firestations/{address}")
    public ResponseEntity<Object> updateFireStation(@PathVariable("address") String address, @RequestBody FireStation fireStation){
        FireStation currentFireStation = fireStationService.findFireStationByAddress(address);
        if (currentFireStation != null){
            String station = fireStation.getStation();
            if (station != null){
                currentFireStation.setStation(station);
            }
            fireStationService.updateFireStation(currentFireStation);
            logger.info(""+fireStation+ " is updated !");
            return new ResponseEntity<>(fireStationService.createFireStation(currentFireStation), HttpStatus.OK);
        }else {
            logger.error("Failed to update :" +fireStation);
            return new ResponseEntity<>(new EmptyJsonResponse(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("firestations/{address}")
    public List<FireStation> deleteFireStation(@PathVariable ("address") String address){
        logger.info("FireStation " +address + " is deleted !");
        return fireStationService.deleteFireStation(address);
    }

    //localhost:8080/phoneAlert?firestation=<firestation_number>
    @GetMapping("phoneAlert")
    public LinkedHashSet<String> getPhoneNumberForAStation(@RequestParam("firestation") String numberStation){
        logger.info("Fire Station"+numberStation+"is queried to get phones.");
        return fireStationService.getPhoneNumberForAStation(numberStation);
    }

    //localhost:8080/fire?address=<address>
    @GetMapping("fire")
    public FireDto findStationByAddress(@RequestParam("address") String address) throws ParseException {
        logger.info("Station address"+address+"is queried to get stations.");
        return fireStationService.findStationByAddress(address);
    }

    //localhost:8080/firestation?stationNumber=<station_number>
    @GetMapping("firestation")
    public PersonCoveredByFireStationDto findPersonsByStationNumber(@RequestParam("stationNumber") String numberStation) throws ParseException {
        logger.info("Station number" +numberStation+ "is queried to get persons.");
        return fireStationService.findPersonsByStationNumber(numberStation);
    }

    //localhost:8080/flood/stations?stations=<a list of station_number>
    @GetMapping("flood/stations")
    public Map<String, Set<PersonWithMedicalRecord>> findAllPersonByStation(@RequestParam("stations") List<String> stationsNumber) throws ParseException {
        logger.info("Stations " + stationsNumber + " is queried to get persons.");
        return fireStationService.findAllPersonByStation(stationsNumber);
    }
}

package com.safetynetalerts.SafetynetAlerts.repository;

import com.safetynetalerts.SafetynetAlerts.database.DataStore;
import com.safetynetalerts.SafetynetAlerts.model.DTO.FireDto;
import com.safetynetalerts.SafetynetAlerts.model.DTO.PersonWithMedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.FireStation;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.*;

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
    // This method return the list of phone number of the person related to a specific number of a fire station
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

    //localhost:8080/fire?address=<address>
    // This method return a list of persons who live in a specific address with the affected number of firestation
    public FireDto findStationByAddress(String address) throws ParseException {
        FireDto fireDto = new FireDto();
        List<PersonWithMedicalRecord> personWithMedicalRecordList = new ArrayList<>();
        List<String> numberStation = new ArrayList<>();
        for (FireStation fireStation: this.getAllFireStations())
            if (fireStation.getAddress().equals(address)){
                numberStation.add(fireStation.getStation());
            }

        for (Person person : personRepository.findPersonByAddress(address)){
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String phone = person.getPhone();
            MedicalRecord medicalRecord = medicalRecordRepository.findMedicalRecordByName(firstName,lastName);
            Integer age = medicalRecord.getAge();
            List<String> medications = medicalRecord.getMedications();
            List<String> allergies = medicalRecord.getAllergies();
            personWithMedicalRecordList.add(new PersonWithMedicalRecord(firstName,lastName, phone,age,medications,allergies));
        }
        fireDto.setPersonWithMedicalRecordDtoList(personWithMedicalRecordList);
        fireDto.setStationsNumbers(numberStation);
        return fireDto;


    }



}

package com.safetynetalerts.SafetynetAlerts.repository;


import com.safetynetalerts.SafetynetAlerts.database.DataStore;
import com.safetynetalerts.SafetynetAlerts.model.DTO.AChild;
import com.safetynetalerts.SafetynetAlerts.model.DTO.ChildAlertDto;
import com.safetynetalerts.SafetynetAlerts.model.MedicalRecord;
import com.safetynetalerts.SafetynetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MedicalRecordRepository {

    DataStore dataStore;
    PersonRepository personRepository;

    @Autowired
    @Lazy
    public MedicalRecordRepository(DataStore dataStore, PersonRepository personRepository) {
        this.dataStore = dataStore;
        this.personRepository = personRepository;
    }

    // This method return the list of medicalrecord in the Json file
    public List<MedicalRecord> getAllMedicalRecords(){
        return dataStore.getMedicalrecords();
    }

    // This method return a medicalrecord by given firstname and lastname
    public MedicalRecord findMedicalRecordByName(String firstName, String lastName){
        List<MedicalRecord> medicalRecordList = this.getAllMedicalRecords();
        for (MedicalRecord medicalRecord : medicalRecordList){
            if ((medicalRecord.getFirstName().equals(firstName)) && (medicalRecord.getLastName().equals(lastName))){
                return medicalRecord;
            }
        }return null;
    }
    // This method allows to add a new medicalrecord to the list of persons
    public List<MedicalRecord> addMedicalRecord(MedicalRecord medicalRecord){
        List<MedicalRecord> medicalRecordList= this.getAllMedicalRecords();
        for (MedicalRecord medicalRecord1 : medicalRecordList){
            if (medicalRecord1.getFirstName().equals(medicalRecord1.getFirstName()) && medicalRecord1.getLastName().equals(medicalRecord.getLastName()))
                break;
            else
                medicalRecordList.add(medicalRecord);
                break;
        }
        return medicalRecordList;
    }

    // This method allows to update the informations of a medicalrecord
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord){
        String birthdate = medicalRecord.getBirthdate();
        medicalRecord.setBirthdate(birthdate);

        List<String> medications = medicalRecord.getMedications();
        medicalRecord.setMedications(medications);

        List<String> allergies = medicalRecord.getAllergies();
        medicalRecord.setAllergies(allergies);

        if ((medicalRecord.getBirthdate().equals(birthdate)) && (medicalRecord.getMedications().equals(medications)) && (medicalRecord.getAllergies().equals(allergies))){
            return null;
        }else {
            return medicalRecord;
        }
    }

    // This method allows to delete a medicalrecord from a list of medicalrecord
    public List<MedicalRecord> deleteMedicalRecord(String firstName, String lastName){
        List<MedicalRecord> medicalRecordList = this.getAllMedicalRecords();
        for (MedicalRecord medicalRecord : medicalRecordList){
            if ((medicalRecord.getFirstName().equals(firstName)) && (medicalRecord.getLastName().equals(lastName))){
                medicalRecordList.remove(medicalRecord);
                break;
            }
        }return medicalRecordList;
    }


    //localhost:8080/childAlert?address=<address>
    // This method return a list of children and adults who lives in an address
    public ChildAlertDto getChildAlert(String address) throws ParseException {
        ChildAlertDto childAlertDto = new ChildAlertDto();
        List<AChild> childList = new ArrayList<>();
        List<Person> memberFamily = new ArrayList<>();
        List<Person> personList = personRepository.findPersonByAddress(address);
       /* if (personList.size() == 0 )
            return null;*/
        for (Person person : personList){
            String firstname = person.getFirstName();
            String lastname = person.getLastName();

            if (person.getAddress().equals(address)){
                MedicalRecord medicalRecord = this.findMedicalRecordByName(firstname,lastname);
                if (medicalRecord.getAge() <= 18){
                    childList.add(new AChild(medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getAge()));
                }
                if ((person.getLastName().equals(medicalRecord.getLastName())&&(medicalRecord.getAge() > 18))){
                        memberFamily.add(person);
                    }
            }else {
                return null;
            }
            childAlertDto.setChildren(childList);
            childAlertDto.setFamilyMembers(memberFamily);
        }
        if (childList.isEmpty())
            childAlertDto.setFamilyMembers(new ArrayList<>());

        return childAlertDto;
    }


}

package com.safetynetalerts.SafetynetAlerts.model.DTO;

import com.safetynetalerts.SafetynetAlerts.model.Person;

import java.util.ArrayList;
import java.util.List;

public class ChildAlertDto {

        private List<AChild> children;
        private List<Person> familyMembers;

        public ChildAlertDto() {
        }

        public ChildAlertDto(List<AChild> children, List<Person> familyMembers) {
            this.children = new ArrayList<>(children) ;
            this.familyMembers = new ArrayList<>(familyMembers);
        }

        public List<AChild> getChildren() {
            return new ArrayList<>(children);
        }

        public void setChildren(List<AChild> children) {
            this.children = new ArrayList<>(children);
        }

        public List<Person> getFamilyMembers() {
            return new ArrayList<>(familyMembers);
        }

        public void setFamilyMembers(List<Person> familyMembers) {
            this.familyMembers = new ArrayList<>(familyMembers);
        }

    }

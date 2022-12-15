package com.safetynetalerts.SafetynetAlerts.model.DTO;

import com.safetynetalerts.SafetynetAlerts.model.Person;

import java.util.List;

public class ChildAlertDto {

        private List<AChild> children;
        private List<Person> familyMembers;

        public ChildAlertDto() {
        }

        public ChildAlertDto(List<AChild> children, List<Person> familyMembers) {
            this.children = children;
            this.familyMembers = familyMembers;
        }

        public List<AChild> getChildren() {
            return children;
        }

        public void setChildren(List<AChild> children) {
            this.children = children;
        }

        public List<Person> getFamilyMembers() {
            return familyMembers;
        }

        public void setFamilyMembers(List<Person> familyMembers) {
            this.familyMembers = familyMembers;
        }

    }

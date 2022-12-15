package com.safetynetalerts.SafetynetAlerts.model.DTO;

public class AChild {

        private String firstname;
        private String lastName;
        private Integer age;

        public AChild() {
        }

        public AChild(String firstname, String lastName, Integer age) {
            this.firstname = firstname;
            this.lastName = lastName;
            this.age = age;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

    }

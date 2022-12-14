package com.safetynetalerts.SafetynetAlerts.model.DTO;

public class PersonCoveredBySpecificFireStation {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;
   // private Integer countChildren;
    //private Integer countAdults;

    public PersonCoveredBySpecificFireStation(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    /*public PersonCoveredBySpecificFireStation(String firstName, String lastName, String address, String phone, Integer countChildren, Integer countAdults) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        //this.countChildren = countChildren;
        //this.countAdults = countAdults;
    }

     */

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

   /* public Integer getCountChildren() {
        return countChildren;
    }

    public void setCountChildren(Integer countChildren) {
        this.countChildren = countChildren;
    }

    public Integer getCountAdults() {
        return countAdults;
    }

    public void setCountAdults(Integer countAdults) {
        this.countAdults = countAdults;
    }
*/
    @Override
    public String toString() {
        return "PersonCoveredBySpecificFireStation{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
               // ", countChildren=" + countChildren +
                //", countAdults=" + countAdults +
                '}';
    }
}

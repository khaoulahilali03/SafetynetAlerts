package com.safetynetalerts.SafetynetAlerts.model;

public class FireStation {

    private String address;
    public String stationNumber;

    public FireStation() {
    }

    public FireStation(String address, String stationNumber) {
        this.address = address;
        this.stationNumber = stationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    @Override
    public String toString() {
        return "FireStation{" +
                "address='" + address + '\'' +
                ", stationNumber='" + stationNumber + '\'' +
                '}';
    }
}

package com.safetynetalerts.SafetynetAlerts.model;

public class FireStation {

    private String address;
    private String station;

    public FireStation() {
    }

    public FireStation(String address, String stationNumber) {
        this.address = address;
        this.station = stationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "FireStation{" +
                "address='" + address + '\'' +
                ", stationNumber='" + station + '\'' +
                '}';
    }
}

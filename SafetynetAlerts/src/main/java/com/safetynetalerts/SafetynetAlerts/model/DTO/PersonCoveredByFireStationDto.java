package com.safetynetalerts.SafetynetAlerts.model.DTO;

import java.util.HashSet;
import java.util.Set;

public class PersonCoveredByFireStationDto {
    private Set<APerson> personSet;
    private int adult_count;
    private int child_count;

    public PersonCoveredByFireStationDto() {
    }

    public PersonCoveredByFireStationDto(Set<APerson> personSet, int adult_count, int child_count) {
        this.personSet = new HashSet<>(personSet);
        this.adult_count = adult_count;
        this.child_count = child_count;
    }

    public Set<APerson> getPersonSet() {
        return new HashSet<>(personSet);
    }

    public void setPersonSet(Set<APerson> personSet) {
        this.personSet = new HashSet<>(personSet);
    }

    public int getAdult_count() {
        return adult_count;
    }

    public void setAdult_count(int adult_count) {
        this.adult_count = adult_count;
    }

    public int getChild_count() {
        return child_count;
    }

    public void setChild_count(int child_count) {
        this.child_count = child_count;
    }

}

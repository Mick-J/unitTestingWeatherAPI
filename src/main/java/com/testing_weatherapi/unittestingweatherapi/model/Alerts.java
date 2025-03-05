package com.testing_weatherapi.unittestingweatherapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Alerts {
    private Location location;
    private List<Alert> alerts = new ArrayList<>();

    public Alerts() {
    }

    public Alerts(Location location, List<Alert> alerts) {
        this.location = location;
        this.alerts = alerts;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    @Override
    public String toString() {
        return "Alerts{" +
                "location: {" + location +
                "}, alerts: {" + alerts +
                '}';
    }
}

package com.testing_weatherapi.unittestingweatherapi.model;

public class Location {
    private String city;
    private String region;
    private String country;
    private String localtime;

    public Location(String city, String region, String country, String localtime) {
        this.city = city;
        this.region = region;
        this.country = country;
        this.localtime = localtime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", localtime='" + localtime + '\'' +
                '}';
    }
}
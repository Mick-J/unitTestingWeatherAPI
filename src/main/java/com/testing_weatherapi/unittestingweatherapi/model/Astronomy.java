package com.testing_weatherapi.unittestingweatherapi.model;


public class Astronomy {
    private Location location;
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;
    private String moon_phase;
    private boolean is_moon_up;
    private boolean is_sun_up;

    public Astronomy() {
    }

    public Astronomy(Location location, String sunrise, String sunset, String moonrise, String moonset, String moon_phase, boolean is_moon_up, boolean is_sun_up) {
        this.location = location;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moon_phase = moon_phase;
        this.is_moon_up = is_moon_up;
        this.is_sun_up = is_sun_up;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getMoon_phase() {
        return moon_phase;
    }

    public void setMoon_phase(String moon_phase) {
        this.moon_phase = moon_phase;
    }

    public boolean isIs_moon_up() {
        return is_moon_up;
    }

    public void setIs_moon_up(boolean is_moon_up) {
        this.is_moon_up = is_moon_up;
    }

    public boolean isIs_sun_up() {
        return is_sun_up;
    }

    public void setIs_sun_up(boolean is_sun_up) {
        this.is_sun_up = is_sun_up;
    }

    @Override
    public String toString() {
        return "Astronomy{" +
                "location : {" + location +
                "}, " +
                "sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", moonrise='" + moonrise + '\'' +
                ", moonset='" + moonset + '\'' +
                ", moon_phase='" + moon_phase + '\'' +
                ", is_moon_up=" + is_moon_up +
                ", is_sun_up=" + is_sun_up +
                '}';
    }
}

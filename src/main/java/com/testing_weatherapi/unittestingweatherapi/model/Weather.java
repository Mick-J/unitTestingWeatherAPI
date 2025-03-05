package com.testing_weatherapi.unittestingweatherapi.model;

public class Weather {
    private Location location;
    private double temperature;
    private String condition;
    private double windSpeed;

    public Weather() {
    }

    public Weather(Location location, double temperature, String condition, double windSpeed) {
        this.location = location;
        this.temperature = temperature;
        this.condition = condition;
        this.windSpeed = windSpeed;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "Weather{" +
                " location= {'" + location.toString() + '\'' +
                "}, temperature=" + temperature +
                ", condition='" + condition + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                '}';
    }
}

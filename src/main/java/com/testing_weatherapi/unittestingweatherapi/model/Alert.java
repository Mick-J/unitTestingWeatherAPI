package com.testing_weatherapi.unittestingweatherapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Alert {
    private String headline;
    private String severity;
    private String areas;
    private String category;
    private String effective;
    private String expires;

    public Alert() {
    }

    public Alert(String headline, String severity, String areas, String category, String effective, String expires) {
        this.headline = headline;
        this.severity = severity;
        this.areas = areas;
        this.category = category;
        this.effective = effective;
        this.expires = expires;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "headline='" + headline + '\'' +
                ", severity='" + severity + '\'' +
                ", areas='" + areas + '\'' +
                ", category='" + category + '\'' +
                ", effective='" + effective + '\'' +
                ", expires='" + expires + '\'' +
                '}';
    }
}

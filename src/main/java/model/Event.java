package model;

import java.util.Objects;

/**
 * Event model object used to store info passed from database
 */
public class Event {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private Float latitude;
    private Float longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;

    /**
     * Constructor to build object
     * @param eventID
     * @param username
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String eventID, String username, String personID, Float latitude, Float longitude,
                 String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Returns a string representation of the Event object
     * @return String
     */

    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", associatedUsername='" + associatedUsername + '\'' +
                ", personID='" + personID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", eventType='" + eventType + '\'' +
                ", year=" + year +
                '}';
    }

    /**
     * Returns a hashcode for the Event object
     * @return int hashcode
     */

    @Override
    public int hashCode() {
        return Objects.hash(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);
    }

    /**
     * Returns a boolean for comparision of Event objects
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventID, event.eventID) && Objects.equals(associatedUsername, event.associatedUsername) && Objects.equals(personID, event.personID) && Objects.equals(latitude, event.latitude) && Objects.equals(longitude, event.longitude) && Objects.equals(country, event.country) && Objects.equals(city, event.city) && Objects.equals(eventType, event.eventType) && Objects.equals(year, event.year);
    }

}

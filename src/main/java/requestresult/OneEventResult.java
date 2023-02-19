package requestresult;

public class OneEventResult {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private Float latitude;
    private Float longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;
    private boolean success;

    private String message;

    /**
     * Constructor that takes parameters necessary to build the object
     * @param eventID
     * @param associatedUsername
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     * @param message
     * @param success
     */

    public OneEventResult(String eventID, String associatedUsername, String personID, Float latitude, Float longitude, String country, String city, String eventType, Integer year, boolean success, String message) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
        this.message = message;
    }
}

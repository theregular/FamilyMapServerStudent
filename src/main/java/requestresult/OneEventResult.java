package requestresult;
/**
 * Object used in the OneEvent  process to transfer result information
 */
public class OneEventResult extends Result {
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
     * Constructor
     * @param success
     */

    public OneEventResult(boolean success) {
        this.eventID = null;
        this.associatedUsername = null;
        this.personID = null;
        this.latitude = null;
        this.longitude = null;
        this.country = null;
        this.city = null;
        this.eventType = null;
        this.year = null;
        this.success = success;
        this.message = null;
    }

    /**
     * Fills with Event info (doesn't set message or success, use inherited methods for that)
     * @param eventID
     * @param associatedUsername
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */

    public void setInfo(String eventID, String associatedUsername, String personID, Float latitude, Float longitude, String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

}

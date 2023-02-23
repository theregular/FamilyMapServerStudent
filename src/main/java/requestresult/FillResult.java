package requestresult;

public class FillResult {
    private boolean success;
    private String message;

    /**
     * Constructor that takes parameters necessary to build the object
     * @param success
     * @param message
     */
    public FillResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package requestresult;
/**
 * Object used in the Load process to transfer result information
 */
public class LoadResult {
    private boolean success;
    private String message;

    /**
     * Constructor that takes parameters necessary to build the object
     * @param success
     * @param message
     */
    public LoadResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

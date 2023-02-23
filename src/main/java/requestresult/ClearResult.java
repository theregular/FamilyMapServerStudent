package requestresult;
/**
 * Object used in the Clear process to transfer result information
 */
public class ClearResult {
    private boolean success;
    private String message;

    public ClearResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

package requestresult;

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

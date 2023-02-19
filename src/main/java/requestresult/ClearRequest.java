package requestresult;

public class ClearRequest {
    private boolean success;

    public ClearRequest(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

package requestresult;

public class FillRequest {
    private boolean success;

    public FillRequest(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

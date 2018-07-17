package sp.sample.mitac.shared;

public class CustomResult<T> {
    private int code;
    private String message;
    private T result;

    public CustomResult() {
        this.code = 0;
        this.message = "";
        this.result = null;
    }
    public CustomResult(CustomCode code) {
        this.code = code.getCode();
        this.message = code.getDescription();
        this.result = null;
    }
    public CustomResult(CustomException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.result = null;
    }
    public CustomResult(T result) {
        this.code = 0;
        this.message = "";
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean successful() {
        return this.code == 0;
    }
}
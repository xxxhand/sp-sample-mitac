package sp.sample.mitac.shared;

public class CustomException extends Exception {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CustomException(String message) {
        super(message);
        this.code = 500;
    }
    public CustomException(int code, String message) {
        super(message);
        this.code = code;
    }
    public CustomException(CustomCode customCode) {
        super(customCode.getDescription());
        this.code = customCode.getCode();
    }

}

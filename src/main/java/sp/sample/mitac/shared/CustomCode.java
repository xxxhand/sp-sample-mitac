package sp.sample.mitac.shared;

public enum CustomCode {
    SUCCESS(0, "Success"),
    PARSE_MODEL_ERROR(1001, "Parse json to model error"),
    STRINGNITY_JSON_ERROR(1002, "Parse to json string error"),
    CRC_INVALID(1003, "CRC is invalid"),
    EQIP_NOT_FOUND(1004, "Not found equipment"),
    EXCEPTION_ERROR(9999, "Exception error")
    ;

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private CustomCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}

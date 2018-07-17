package sp.sample.mitac.infra.orms.mongo;

public class ReceiveDataEmbedded {
    private String value;
    private int number;
    private String unit;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ReceiveDataEmbedded() {}
    private ReceiveDataEmbedded(Builder builder) {
        this.number = builder.number;
        this.unit = builder.unit;
        this.value = builder.value;
    }

    public static class Builder {
        private String value;
        private int number;
        private String unit;

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withNumber(int number) {
            this.number = number;
            return this;
        }

        public Builder withUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public ReceiveDataEmbedded build() {
            return new ReceiveDataEmbedded(this);
        }
    }
}

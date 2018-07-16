package sp.sample.mitac.domain.valueObjects;

public class ReceiveData {
    private String value;
    private int number;
    private String unit;

    private ReceiveData(Builder builder) {
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

        public ReceiveData build() {
            return new ReceiveData(this);
        }
    }
}

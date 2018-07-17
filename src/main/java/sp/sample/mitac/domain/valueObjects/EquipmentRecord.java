package sp.sample.mitac.domain.valueObjects;

import java.util.HashMap;
import java.util.Map;

public class EquipmentRecord {
    private String equipmentName;
    private int typeId;
    private Map<String, ReceiveData> data;

    public String getEquipmentName() {
        return equipmentName;
    }

    public int getTypeId() {
        return typeId;
    }

    public Map<String, ReceiveData> getData() {
        return data;
    }

    private EquipmentRecord(Builder builder) {
        this.equipmentName = builder.equipmentName;
        this.typeId = builder.typeId;
        this.data = builder.data;
    }

    public void addData(String dataKey, ReceiveData dataValue) {
        if (this.data == null) {
            this.data = new HashMap<>();
        }
        this.data.put(dataKey, dataValue);
    }
    public static class Builder {
        private String equipmentName;
        private int typeId;
        private Map<String, ReceiveData> data;

        public Builder withEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
            return this;
        }

        public Builder withTypeId(int typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder withData(Map<String, ReceiveData> data) {
            this.data = data;
            return this;
        }

        public EquipmentRecord build() {
            return new EquipmentRecord(this);
        }
    }
}

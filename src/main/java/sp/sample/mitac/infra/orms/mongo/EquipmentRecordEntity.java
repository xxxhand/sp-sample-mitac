package sp.sample.mitac.infra.orms.mongo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

import java.util.HashMap;
import java.util.Map;

@Entity(value = "equipment_records", noClassnameStored = true)
@Indexes(@Index(fields = @Field(value = "typeId", type = IndexType.ASC)))
public class EquipmentRecordEntity {
    @Id
    @JsonIgnore
    private ObjectId _id;
    private int typeId;
    private String equipmentName;
    @Embedded
    private Map<String, ReceiveDataEmbedded> data;
    private long createAt;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Map<String, ReceiveDataEmbedded> getData() {
        return data;
    }

    public void setData(Map<String, ReceiveDataEmbedded> data) {
        this.data = data;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public void addData(String dataKey, ReceiveDataEmbedded dataValue) {
        if (this.data == null) {
            this.data = new HashMap<>();
        }
        this.data.put(dataKey, dataValue);
    }
}

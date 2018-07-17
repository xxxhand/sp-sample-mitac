package sp.sample.mitac.infra.repositories;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import sp.sample.mitac.domain.repositories.IEquipmentRecordRepository;
import sp.sample.mitac.domain.valueObjects.EquipmentRecord;
import sp.sample.mitac.domain.valueObjects.ReceiveData;
import sp.sample.mitac.infra.orms.mongo.EquipmentRecordEntity;
import sp.sample.mitac.infra.orms.mongo.ReceiveDataEmbedded;
import sp.sample.mitac.shared.CustomException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EquipmentRecordRepository implements IEquipmentRecordRepository {

    @Inject
    @Named("main_mongo")
    private Datastore ds;

    @Override
    public void save(EquipmentRecord record) throws CustomException {
        EquipmentRecordEntity entity = new EquipmentRecordEntity();
        entity.setTypeId(record.getTypeId());
        entity.setEquipmentName(record.getEquipmentName());
        entity.setCreateAt(new Date().getTime());
        for (Map.Entry<String, ReceiveData> x: record.getData().entrySet()) {
            entity.addData(x.getKey(), new ReceiveDataEmbedded.Builder()
                    .withUnit(x.getValue().getUnit())
                    .withValue(x.getValue().getValue())
                    .withNumber(x.getValue().getNumber())
                    .build());
        }

        this.ds.save(entity);

    }

    @Override
    public List<EquipmentRecord> findAllByType(int typeId) throws CustomException {
        final Query<EquipmentRecordEntity> q = this.ds.createQuery(EquipmentRecordEntity.class)
                .field("typeId").equal(typeId);
        final List<EquipmentRecordEntity> entities = q.asList();
        final List<EquipmentRecord> res = new ArrayList<>();
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        EquipmentRecord record;
        for (EquipmentRecordEntity x: entities) {
            record = new EquipmentRecord.Builder()
                    .withTypeId(x.getTypeId())
                    .withEquipmentName(x.getEquipmentName())
                    .build();

            for (Map.Entry<String, ReceiveDataEmbedded> y: x.getData().entrySet()) {
                record.addData(y.getKey(), new ReceiveData.Builder()
                        .withValue(y.getValue().getValue())
                        .withNumber(y.getValue().getNumber())
                        .withUnit(y.getValue().getUnit())
                        .build()
                );
            }

            res.add(record);
        }

        return res;
    }
}

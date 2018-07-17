package sp.sample.mitac.domain.repositories;

import sp.sample.mitac.domain.valueObjects.EquipmentRecord;
import sp.sample.mitac.shared.CustomException;

import java.util.List;

public interface IEquipmentRecordRepository {
    void save(EquipmentRecord record) throws CustomException;
    List<EquipmentRecord> findAllByType(int typeId) throws CustomException;
}

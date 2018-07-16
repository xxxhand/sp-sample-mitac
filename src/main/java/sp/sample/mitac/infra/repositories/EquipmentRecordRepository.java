package sp.sample.mitac.infra.repositories;

import sp.sample.mitac.domain.repositories.IEquipmentRecordRepository;
import sp.sample.mitac.domain.valueObjects.EquipmentRecord;
import sp.sample.mitac.shared.CustomException;

import java.util.List;

public class EquipmentRecordRepository implements IEquipmentRecordRepository {
    @Override
    public void save(EquipmentRecord record) throws CustomException {

    }

    @Override
    public List<EquipmentRecord> findAllByType(String equipmentType) throws CustomException {
        return null;
    }
}

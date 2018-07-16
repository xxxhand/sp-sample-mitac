package sp.sample.mitac.domain.factories.interfaces;

import sp.sample.mitac.domain.valueObjects.EquipmentRecord;
import sp.sample.mitac.shared.CustomException;

public interface IEquipmentRecordFactory {
    EquipmentRecord create(int typeId, int[] msg) throws CustomException;
}

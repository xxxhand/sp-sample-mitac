package sp.sample.mitac.domain.factories;

import com.fasterxml.jackson.databind.JsonNode;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import sp.sample.mitac.domain.factories.interfaces.IEquipmentRecordFactory;
import sp.sample.mitac.domain.valueObjects.EquipmentRecord;
import sp.sample.mitac.domain.valueObjects.ReceiveData;
import sp.sample.mitac.shared.CustomCode;
import sp.sample.mitac.shared.CustomException;
import sp.sample.mitac.shared.UDPCoreConfig;
import sp.sample.mitac.shared.interfaces.IJsonClient;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EquipmentRecordFactory implements IEquipmentRecordFactory {

    @Override
    public EquipmentRecord create(int typeId, int[] msg) throws CustomException {
//        System.out.println(typeId);
        final JsonNode equipmentAryNode = UDPCoreConfig.getConfig().get("equipments");
        JsonNode equipmentNode = null;
        for (JsonNode eNode: equipmentAryNode) {
            if (eNode.get("typeId").asInt() == typeId) {
                equipmentNode = eNode;
                break;
            }
        }
        if (equipmentNode == null) {
            throw new CustomException(CustomCode.EQIP_NOT_FOUND);
        }

        JsonNode dataNode = equipmentNode.get("data");
        Map<String, ReceiveData> mapData = new HashMap<>();

        Iterator<String> fieldNames = dataNode.fieldNames();
        String fieldName;
        JsonNode fieldNode;
        JsonNode valueIndexesNode;
        JsonNode formulaNode;
        int nodeIndex = 0;
        ReceiveData receiveData;

        while (fieldNames.hasNext()) {
            fieldName = fieldNames.next();
            fieldNode = dataNode.get(fieldName);
            valueIndexesNode = fieldNode.get("valueIndexes");
            formulaNode = fieldNode.get("formula");

            double val = 0;
            if (formulaNode == null) {
                for (JsonNode vd: valueIndexesNode) {
                    val += msg[vd.asInt()];
                }
            } else {
                Function F = new Function(formulaNode.asText());
                StringBuilder sb = new StringBuilder("F(");
                for (JsonNode vd: valueIndexesNode) {
                    sb.append(msg[vd.asInt()]);
                    sb.append(",");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(")");
                Expression e1 = new Expression(sb.toString(), F);
                val = e1.calculate();
            }


            receiveData = new ReceiveData.Builder()
                    .withNumber(nodeIndex)
                    .withValue(String.valueOf(val))
                    .withUnit(fieldNode.get("unit").asText())
                    .build();

            mapData.put(fieldName, receiveData);
            nodeIndex++;
        }

        EquipmentRecord record = new EquipmentRecord.Builder()
                .withTypeId(typeId)
                .withEquipmentName(equipmentNode.get("equipmentName").asText())
                .withData(mapData)
                .build();

        return record;
    }
}

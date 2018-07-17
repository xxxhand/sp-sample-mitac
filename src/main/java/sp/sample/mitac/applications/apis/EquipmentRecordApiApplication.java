package sp.sample.mitac.applications.apis;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import sp.sample.mitac.applications.apis.interfaces.IEquipmentRecordApiApplication;
import sp.sample.mitac.domain.repositories.IEquipmentRecordRepository;
import sp.sample.mitac.domain.valueObjects.EquipmentRecord;
import sp.sample.mitac.domain.valueObjects.ReceiveData;
import sp.sample.mitac.shared.CustomCode;
import sp.sample.mitac.shared.CustomException;
import sp.sample.mitac.shared.CustomResult;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static sp.sample.mitac.bootstrap.HttpCoreFinalHandle.responseHandler;

public class EquipmentRecordApiApplication implements IEquipmentRecordApiApplication {

    private Vertx vertx;
    private IEquipmentRecordRepository equipmentRecordRepository;

    @Inject
    public EquipmentRecordApiApplication(Vertx vertx, IEquipmentRecordRepository equipmentRecordRepository) {
        this.vertx = vertx;
        this.equipmentRecordRepository = equipmentRecordRepository;
    }

    @Override
    public void find(RoutingContext ctx) {
        this.vertx.executeBlocking(fut -> {
            try {
                String typeIdStr = ctx.request().getParam("eqp");
                if (StringUtils.isBlank(typeIdStr)) {
                    throw new CustomException(CustomCode.EQIP_NOT_FOUND);
                }
                int typeId = Integer.parseInt(typeIdStr);
                List<EquipmentRecord> records = this.equipmentRecordRepository.findAllByType(typeId);
                List<Map<String, ReceiveData>> data = new ArrayList<>();
                if (records == null || records.isEmpty()) {
                    fut.complete(new CustomResult<>(data));
                    return;
                }
                records.forEach(x -> data.add(x.getData()));
                fut.complete(new CustomResult<>(data));

            } catch (CustomException ex) {
                fut.complete(new CustomResult<>(ex));
            }
        }, false, (AsyncResult<CustomResult<?>> ars) -> responseHandler(ars, ctx));
    }
}

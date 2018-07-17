package sp.sample.mitac.applications.apis;

import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import sp.sample.mitac.applications.apis.interfaces.IEquipmentRecordApiApplication;
import sp.sample.mitac.domain.repositories.IEquipmentRecordRepository;

import javax.inject.Inject;

public class EquipmentRecordApiApplication implements IEquipmentRecordApiApplication {

    private Vertx vertx;
    private IEquipmentRecordRepository equipmentRecordRepository;

    public EquipmentRecordApiApplication(Vertx vertx, IEquipmentRecordRepository equipmentRecordRepository) {
        this.vertx = vertx;
        this.equipmentRecordRepository = equipmentRecordRepository;
    }

    @Inject
    public EquipmentRecordApiApplication(IEquipmentRecordRepository equipmentRecordRepository) {
        this.equipmentRecordRepository = equipmentRecordRepository;
    }

    @Override
    public void find(RoutingContext ctx) {
        ctx.response().end("app called");
    }
}

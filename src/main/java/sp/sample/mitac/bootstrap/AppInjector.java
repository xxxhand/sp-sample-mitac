package sp.sample.mitac.bootstrap;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import io.vertx.core.Vertx;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.Datastore;
import sp.sample.mitac.applications.UDPCoreApplication;
import sp.sample.mitac.applications.apis.EquipmentRecordApiApplication;
import sp.sample.mitac.applications.apis.interfaces.IEquipmentRecordApiApplication;
import sp.sample.mitac.applications.interfaces.IUDPCoreApplication;
import sp.sample.mitac.domain.factories.EquipmentRecordFactory;
import sp.sample.mitac.domain.factories.interfaces.IEquipmentRecordFactory;
import sp.sample.mitac.domain.repositories.IEquipmentRecordRepository;
import sp.sample.mitac.domain.services.ValidateService;
import sp.sample.mitac.domain.services.interfaces.IValidateService;
import sp.sample.mitac.infra.repositories.EquipmentRecordRepository;
import sp.sample.mitac.infra.tools.MongoDBConnector;
import sp.sample.mitac.shared.CustomJson;
import sp.sample.mitac.shared.HttpCoreConfig;
import sp.sample.mitac.shared.interfaces.IJsonClient;

public class AppInjector extends AbstractModule {
    private static AppInjector appInjector = null;
    private static Injector injector = null;


    public static void setInjector(Injector injector) { injector = injector; }
    public static Injector getInjector() { return injector; }

    public static AppInjector initialize(Vertx vertx) {
        if (appInjector == null) {
            appInjector = new AppInjector(vertx);
        }

        return appInjector;
    }
    
    private Vertx vertx;
    private AppInjector(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    protected void configure() {

        bind(Vertx.class).toInstance(this.vertx);
        bind(IJsonClient.class).toInstance(CustomJson.defaultSerializer());
        bind(IUDPCoreApplication.class).to(UDPCoreApplication.class).in(Singleton.class);
        bind(IValidateService.class).to(ValidateService.class).in(Singleton.class);
        bind(IEquipmentRecordFactory.class).to(EquipmentRecordFactory.class).in(Singleton.class);
        bind(IEquipmentRecordRepository.class).to(EquipmentRecordRepository.class).in(Singleton.class);
        bind(IEquipmentRecordApiApplication.class).to(EquipmentRecordApiApplication.class).in(Singleton.class);


        JsonNode dbNode = HttpCoreConfig.getConfig().get("databases");
        JsonNode mainNode = dbNode.get("main_mongo");

        final Morphia morphia = new Morphia();
        morphia.mapPackage("sp.sample.mitac.infra.orms.mongo");

        final MongoDBConnector connector = MongoDBConnector.create(mainNode.get("uri").asText(), mainNode.get("dbName").asText());
        Datastore ds = morphia.createDatastore(connector.getDbClient(), connector.getDbName());
        bind(Datastore.class).annotatedWith(Names.named("main_mongo")).toInstance(ds);


        super.configure();

    }
}

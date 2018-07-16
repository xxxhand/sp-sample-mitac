package sp.sample.mitac.bootstrap;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import sp.sample.mitac.applications.UDPCoreApplication;
import sp.sample.mitac.applications.interfaces.IUDPCoreApplication;
import sp.sample.mitac.domain.factories.EquipmentRecordFactory;
import sp.sample.mitac.domain.factories.interfaces.IEquipmentRecordFactory;
import sp.sample.mitac.domain.repositories.IEquipmentRecordRepository;
import sp.sample.mitac.domain.services.ValidateService;
import sp.sample.mitac.domain.services.interfaces.IValidateService;
import sp.sample.mitac.infra.repositories.EquipmentRecordRepository;
import sp.sample.mitac.shared.CustomJson;
import sp.sample.mitac.shared.interfaces.IJsonClient;

public class AppInjector extends AbstractModule {
    private static AppInjector appInjector = null;
    public static synchronized AppInjector defaultInjector() {
        if (appInjector == null) {
            synchronized (AppInjector.class) {
                appInjector = new AppInjector();
            }
        }
        return appInjector;
    }

    private AppInjector() {}

    @Override
    protected void configure() {

        bind(IJsonClient.class).toInstance(CustomJson.defaultSerializer());
        bind(IUDPCoreApplication.class).to(UDPCoreApplication.class).in(Singleton.class);
        bind(IValidateService.class).to(ValidateService.class).in(Singleton.class);
        bind(IEquipmentRecordFactory.class).to(EquipmentRecordFactory.class).in(Singleton.class);
        bind(IEquipmentRecordRepository.class).to(EquipmentRecordRepository.class).in(Singleton.class);

        super.configure();

    }
}

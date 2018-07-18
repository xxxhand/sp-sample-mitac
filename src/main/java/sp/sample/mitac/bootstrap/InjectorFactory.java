package sp.sample.mitac.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.Vertx;

import java.io.File;

/**
 * Created by hand on 2018/7/17.
 */
public class InjectorFactory {
    private static Injector injector = null;
    public static synchronized void create(Vertx vertx, File configFile) {
        if (injector == null) {
            synchronized (Injector.class) {
                injector = Guice.createInjector(new AppInjector(vertx, configFile));
            }
        }
    }
    public static Injector getInjector() { return injector; }
}

package sp.sample.mitac;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import sp.sample.mitac.bootstrap.AppInjector;
import sp.sample.mitac.bootstrap.HttpCoreVerticle;
import sp.sample.mitac.bootstrap.InjectorFactory;
import sp.sample.mitac.bootstrap.UDPCoreRunner;

import java.io.File;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String[] ALLOW_ENVS = { "local" };
    public static void main( String[] args )
    {
        String env = args.length > 0 ? args[0] : "local";
        if (!Arrays.asList(ALLOW_ENVS).contains(env)) {
            System.out.println(String.format("You input an invalid env: %s", env));
            System.out.println("Env must be " + String.join("|", ALLOW_ENVS));
            return;
        }
        String configPath = args.length > 2 ? args[1] : "configs/AppConfig." + env + ".json";
        File configFile  = new File(configPath);
        if (!configFile.exists()) {
            System.out.print("Not found config file at: " + configPath);
            return;
        }

        Vertx vx = Vertx.vertx();

        InjectorFactory.create(vx, configFile);

        _initConfig(vx, configPath).getConfig(cfg -> {
            if (cfg.failed()) {
                System.out.println("Fail to set configs");
                cfg.cause().printStackTrace();
                vx.close();
                return;
            }
            JsonObject c = cfg.result();
            DeploymentOptions opt = new DeploymentOptions();
            opt.setConfig(c);

            vx.deployVerticle(HttpCoreVerticle.class.getName(), opt, evt -> {
                if (evt.failed()) {
                    evt.cause().printStackTrace();
                    vx.close();
                }
            });
        });


        try {
            new UDPCoreRunner().run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }



    }
    private static ConfigRetriever _initConfig(Vertx vertx, String configPath) {
        ConfigStoreOptions csOptions = new ConfigStoreOptions()
                .setType("file")
                .setFormat("json")
                .setConfig(new JsonObject().put("path", configPath));
        ConfigRetrieverOptions crOptions = new ConfigRetrieverOptions().addStore(csOptions);

        return ConfigRetriever.create(vertx, crOptions);
    }
}

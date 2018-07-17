package sp.sample.mitac.bootstrap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;
import sp.sample.mitac.shared.CustomJson;
import sp.sample.mitac.shared.HttpCoreConfig;
import sp.sample.mitac.shared.interfaces.IJsonClient;

public class HttpCoreVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        IJsonClient jsonClient = CustomJson.customSerializer(new ObjectMapper());
        JsonNode appConfig = jsonClient.fromJson(config().toString(), JsonNode.class);
        HttpCoreConfig.setConfig(appConfig.get("coreModule"));

        HttpServerOptions opt = new HttpServerOptions();
        opt.setPort(HttpCoreConfig.getConfig().get("httpPort").asInt());

        vertx.createHttpServer(opt)
                .requestHandler(new HttpCoreRouter().getMainRouter()::accept)
                .listen(x -> {
                    if (x.succeeded()) {
                        System.out.println("Http server up on " + x.result().actualPort());
                        startFuture.complete();
                        return;
                    }
                    startFuture.fail(x.cause());
                });
    }
}

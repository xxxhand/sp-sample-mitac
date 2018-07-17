package sp.sample.mitac.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import sp.sample.mitac.applications.apis.interfaces.IEquipmentRecordApiApplication;

public class HttpCoreRouter {
    private Vertx vertx;
    private Router mainRouter;

    public Router getMainRouter() {
        return mainRouter;
    }

    public HttpCoreRouter(Vertx vertx) {
        Injector injector = AppInjector.getInjector();
        this.vertx = vertx;
        this._initialMainRouters();
        this._initialApiRouters(injector);
    }

    private void _initialMainRouters() {
        this.mainRouter = Router.router(this.vertx);
        this.mainRouter.route().handler(BodyHandler.create());
        this.mainRouter.route("/hello").handler(ctx -> ctx.response().end("Hello world"));
    }
    private void _initialApiRouters(Injector injector) {
        Router apiRouter = Router.router(this.vertx);

        IEquipmentRecordApiApplication equipmentRecordApiApp = injector.getInstance(IEquipmentRecordApiApplication.class);

        apiRouter.get("/equipments").handler(equipmentRecordApiApp::find);


        this.mainRouter.mountSubRouter("/api", apiRouter);
    }
}

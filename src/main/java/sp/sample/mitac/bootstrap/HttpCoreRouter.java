package sp.sample.mitac.bootstrap;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpCoreRouter {
    private Vertx vertx;
    private Router mainRouter;

    public Router getMainRouter() {
        return mainRouter;
    }

    public HttpCoreRouter(Vertx vertx) {
        this.vertx = vertx;
        this._initialMainRouters();
        this._initialApiRouters();
    }

    private void _initialMainRouters() {
        this.mainRouter = Router.router(this.vertx);
        this.mainRouter.route().handler(BodyHandler.create());
        this.mainRouter.route("/hello").handler(ctx -> ctx.response().end("Hello world"));
    }
    private void _initialApiRouters() {
        Router apiRouter = Router.router(this.vertx);



        this.mainRouter.mountSubRouter("/api", apiRouter);
    }
}
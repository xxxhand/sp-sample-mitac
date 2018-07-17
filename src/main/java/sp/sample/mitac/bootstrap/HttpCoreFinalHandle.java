package sp.sample.mitac.bootstrap;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import sp.sample.mitac.shared.CustomCode;
import sp.sample.mitac.shared.CustomResult;

import static sp.sample.mitac.bootstrap.HttpCoreFinalHandle.responseHandler;
/**
 * Created by hand on 2018/7/17.
 */
public class HttpCoreFinalHandle {

    public static void responseHandler(AsyncResult<CustomResult<?>> ars, RoutingContext ctx) {
        if (ars.succeeded()) {
            if (!ars.result().successful()) {
                ctx.response()
                        .putHeader("Content-Type", "application/json")
                        .setStatusCode(HttpResponseStatus.BAD_REQUEST.code())
                        .end(Json.encode(ars.result()));
                return;
            }
            ctx.response()
                    .putHeader("Content-Type", "application/json")
                    .setStatusCode(HttpResponseStatus.OK.code())
                    .end(Json.encode(ars.result()));
            return;
        }

        CustomResult rs = new CustomResult();
        rs.setCode(CustomCode.EXCEPTION_ERROR.getCode());
        rs.setMessage(ars.cause().getMessage());
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                .end(Json.encode(rs));
    }
}

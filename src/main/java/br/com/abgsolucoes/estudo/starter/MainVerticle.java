package br.com.abgsolucoes.estudo.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);
    router.get("/carros").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response.putHeader("content/type", "text/html").end("<h1>TEste</h1>");
      }
    );


    Route route = router.route("/carroes");
    route.handler(c->{
      HttpServerResponse response = c.response();

      response.setChunked(true);
      response.write("Teste\n");
      c.next();
    });

    route.handler(c->{
      HttpServerResponse response = c.response();
      response.write("Teste 2");
      response.end();
    });

    router.get("/comjson").respond( ctx -> Future.succeededFuture(new JsonObject().put("hello", "world")));


    vertx.createHttpServer().requestHandler(router)
    .listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}

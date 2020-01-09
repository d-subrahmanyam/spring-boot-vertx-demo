package com.subbu.boot.vertx.verticles;

import com.subbu.boot.vertx.annotations.VerticleMessage;
import com.subbu.boot.vertx.annotations.Verticles;
import com.subbu.boot.vertx.contracts.IVerticle;
import io.vertx.core.Handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

@Slf4j
@Component
public class ServerVerticle extends AbstractVerticle {

    @Autowired
    private Router router;

    @Autowired
    private ApplicationContext applicationContext;

    private void messageHandler(RoutingContext routingContext) {
        String _message = routingContext.get("route.message");
        log.info("passing the message - {}", _message);
        vertx.eventBus()
                .<String>request(_message, "", result -> {
                    if (result.succeeded()) {
                        routingContext.response()
                                .putHeader("content-type", "application/json")
                                .setStatusCode(200)
                                .end(result.result()
                                        .body());
                    } else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }

    @Override
    public void start() throws Exception {
        super.start();

        Map<String, Object> verticles = applicationContext.getBeansWithAnnotation(Verticles.class);

        verticles.keySet().forEach(verticle -> {
            IVerticle iVerticle = (IVerticle) verticles.get(verticle);
            Verticles verticlesAnnotation = iVerticle.getClass().getAnnotation(Verticles.class);
            VerticleMessage[] verticleMessages = verticlesAnnotation.messages();
            for(VerticleMessage verticleMessage: verticleMessages) {
                log.info("path - {} | message - {}", verticleMessage.path(), verticleMessage.messages());
                router
                        .get(verticleMessage.path())
                        .handler(new Handler<RoutingContext>() {
                            @Override
                            public void handle(RoutingContext routingContext) {
                                log.info("Processing the - {} with message - {}", verticleMessage.path(), verticleMessage.messages());
                                routingContext.put("route.message", verticleMessage.messages());
                                messageHandler(routingContext);
                            }
                        });
            }
        });

        vertx
                .createHttpServer()
                .requestHandler(router)
                .listen(config().getInteger("http.port", 8080));
    }

}
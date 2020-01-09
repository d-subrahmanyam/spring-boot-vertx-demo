package com.subbu.boot.vertx.verticles;

import com.subbu.boot.vertx.annotations.VerticleMessage;
import com.subbu.boot.vertx.annotations.Verticles;
import com.subbu.boot.vertx.annotations.VertxHandler;
import com.subbu.boot.vertx.contracts.IVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import java.util.Map;

@Verticles(messages = {@VerticleMessage(path="/api/authors", messages = "authors.all")})
@Slf4j
public class AuthorVerticle extends AbstractVerticle implements IVerticle {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void start() throws Exception {
        super.start();
        Map<String, Object> handlers = applicationContext.getBeansWithAnnotation(VertxHandler.class);
        log.info("handlers - {}", handlers);
        handlers
                .keySet()
                .forEach(handlerName -> {
                    Handler<Message<String>> handler = (Handler<Message<String>>) handlers.get(handlerName);
                    VertxHandler vertxHandler = handler.getClass().getAnnotation(VertxHandler.class);
                    log.info("message - {} | handler - {}", vertxHandler.message(), handler.getClass());
                    vertx
                            .eventBus()
                            .consumer(vertxHandler.message(), handler);
                });
    }
}

package com.subbu.boot.vertx.components;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class VerticlesRegistrar implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Vertx vertx;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        Map<String, AbstractVerticle> verticles = applicationContext.getBeansOfType(AbstractVerticle.class);
        verticles.values().forEach(verticle -> {
            log.info("deploying - {}", verticle.getClass());
            vertx.deployVerticle(verticle);
        });
    }
}

package com.subbu.boot.vertx.config;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class VertxConfig {

    /**
     * This method creates a bean of Vertx
     * @return
     */
    @Bean("vertx")
    public Vertx vertx() {
        return Vertx.vertx();
    }

    /**
     * This method returns a bean of Router
     * @param vertx
     * @return
     */
    @Bean
    @DependsOn("vertx")
    public Router router(@Autowired Vertx vertx) {
        return Router.router(vertx);
    }
}

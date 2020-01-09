package com.subbu.boot.vertx.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Verticles {

    String value() default "";
    VerticleMessage[] messages();
}

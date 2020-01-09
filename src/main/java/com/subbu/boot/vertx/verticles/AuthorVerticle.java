package com.subbu.boot.vertx.verticles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subbu.boot.vertx.annotations.VerticleMessage;
import com.subbu.boot.vertx.annotations.Verticles;
import com.subbu.boot.vertx.contracts.IVerticle;
import com.subbu.boot.vertx.handlers.AllAuthorsHandler;
import com.subbu.boot.vertx.handlers.OneAuthorHandler;
import com.subbu.boot.vertx.repos.ArticleRepository;
import com.subbu.boot.vertx.repos.AuthorRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;

@Verticles(messages = {@VerticleMessage(path="/api/authors", messages = "authors.all")})
@Slf4j
public class AuthorVerticle extends AbstractVerticle implements IVerticle {

    private final ObjectMapper mapper = Json.mapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AllAuthorsHandler allAuthorsHandler;

    @Autowired
    private OneAuthorHandler oneAuthorHandler;

    @Override
    public void start() throws Exception {
        super.start();
        vertx
                .eventBus()
                .consumer("authors.all", allAuthorsHandler);

        //TODO: Fix this to read the input from the eventbus and pass it onto the handler
        vertx
                .eventBus()
                .consumer("authors.one", oneAuthorHandler);
    }
}

package com.subbu.boot.vertx.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subbu.boot.vertx.repos.ArticleRepository;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AllArticlesHandler implements Handler<Message<String>> {

    private final ObjectMapper mapper = Json.mapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private Vertx vertx;

    /**
     * Something has happened, so handle it.
     *
     * @param event the event to handle
     */
    @Override
    public void handle(Message<String> event) {
        log.info("processing the message - articles.all");
        vertx.<String>executeBlocking(future -> {
            try {
                future.complete(mapper.writeValueAsString(articleRepository.findAll()));
            } catch (JsonProcessingException e) {
                log.error("Failed to serialize result - \n{}", ExceptionUtils.getStackTrace(e));
                future.fail(e);
            }
        }, result -> {
            if (result.succeeded()) {
                event.reply(result.result());
            } else {
                event.reply(result.cause().toString());
            }
        });
    }
}

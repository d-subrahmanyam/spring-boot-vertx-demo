package com.subbu.boot.vertx.verticles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subbu.boot.vertx.annotations.VerticleMessage;
import com.subbu.boot.vertx.annotations.Verticles;
import com.subbu.boot.vertx.contracts.IVerticle;
import com.subbu.boot.vertx.handlers.AllArticlesHandler;
import com.subbu.boot.vertx.repos.ArticleRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Verticles(messages = {
        @VerticleMessage(path="/api/articles", messages = "articles.all"),
        @VerticleMessage(path="/api/article/{id}", messages = "articles.one")
})
@Slf4j
public class ArticleVerticle extends AbstractVerticle implements IVerticle {

    private final ObjectMapper mapper = Json.mapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AllArticlesHandler allArticlesHandler;

    @Override
    public void start() throws Exception {
        super.start();
        vertx
                .eventBus()
                .<String>consumer("articles.all", allArticlesHandler);
    }
}

package com.subbu.boot.vertx.components;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.subbu.boot.vertx.entities.Article;
import com.subbu.boot.vertx.entities.Author;
import com.subbu.boot.vertx.repos.ArticleRepository;
import com.subbu.boot.vertx.repos.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Slf4j
@Component
public class DbBootstrap implements CommandLineRunner {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void run(String... arg0) throws Exception {

        if(authorRepository.count() <= 0) {
            log.info("Wow... Seems like the authors data is empty. Let's seed authors data.");
            Type authorsType = new TypeToken<List<Author>>() {}.getType();
            String usersJson = FileUtils.readFileToString(ResourceUtils.getFile("classpath:author_data.json"), "UTF-8");
            List<Author> authors = new GsonBuilder().create().fromJson(usersJson, authorsType);
            authors.stream().forEach(author -> {
                log.debug("seeding - {}", author);
                this.authorRepository.save(author);
            });
        }

        if(articleRepository.count() <= 0) {
            log.info("Wow... Seems like the articles data is empty. Let's seed articles data.");
            Type articlesType = new TypeToken<List<Article>>() {}.getType();
            String articlesJson = FileUtils.readFileToString(ResourceUtils.getFile("classpath:article_data.json"), "UTF-8");
            List<Article> articles = new GsonBuilder().create().fromJson(articlesJson, articlesType);
            articles.stream().forEach(article -> {
                log.debug("seeding - {}", article);
                this.articleRepository.save(article);
            });
        }
    }
}
package com.subbu.boot.vertx.repos;

import com.subbu.boot.vertx.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
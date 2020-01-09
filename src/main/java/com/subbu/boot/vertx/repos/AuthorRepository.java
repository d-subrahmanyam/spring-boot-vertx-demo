package com.subbu.boot.vertx.repos;

import com.subbu.boot.vertx.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
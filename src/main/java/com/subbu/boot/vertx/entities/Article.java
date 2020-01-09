package com.subbu.boot.vertx.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="ARTICLES")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="article", length = 10000)
    private String article;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author author;
}

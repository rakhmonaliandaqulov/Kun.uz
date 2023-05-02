package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "update_date")
    private LocalDateTime update_date;
    @Column(name = "visible")
    private Boolean visible;
}

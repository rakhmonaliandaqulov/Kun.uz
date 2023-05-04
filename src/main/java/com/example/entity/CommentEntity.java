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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;
    @Column(name = "profile_id")
    private Integer profileId;
    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;
    @Column(name = "article_id")
    private String articleId;
    @OneToOne
    @JoinColumn(name = "reply_id",insertable = false,updatable = false)
    private CommentEntity reply;
    @Column(name = "reply_id")
    private Integer replyId;


    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column
    private Boolean visible=Boolean.TRUE;
}

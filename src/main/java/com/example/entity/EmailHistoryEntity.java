package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message")
    private String  message;

    @Column(name = "email")
    private String email;

    @Column(name =  "created_date")
    private LocalDate createdDate;
}

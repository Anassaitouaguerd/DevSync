package com.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int count = 1;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private String status = "active";

    public Token(String token, User user, String type) {
        this.token = token;
        this.user = user;
        this.type = type;
        this.creationDate = LocalDateTime.now();
    }
}
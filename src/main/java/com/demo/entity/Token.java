package com.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private Long userId;
    public Token() {
    }
    public Token(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }


}

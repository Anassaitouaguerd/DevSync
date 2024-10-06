package com.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = false)
    private String password;

    @Column(nullable = false, unique = false)
    private String adresse;

    @Column(nullable = true, unique = false)
    private Boolean manager = false;

    // Constructors
    public User() {}

    public User(String name, String email , String password, String adresse , Boolean manager) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.adresse = adresse;
        this.manager = manager;
    }

}
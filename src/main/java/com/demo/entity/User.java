package com.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "createdBy")
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "assignedTo")
    private List<Task> assignedTasks;

    // Constructors
    public User() {}
    public User(Long id) {
        this.id = id;
    }

    public User(String name, String email , String password, String adresse , Boolean manager) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.adresse = adresse;
        this.manager = manager;
    }

}
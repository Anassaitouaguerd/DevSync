package com.demo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    private Boolean completed = false;

    @ManyToMany
    @JoinTable(
        name = "task_tags",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "assignedTo")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User createdBy;

    public Task() {
    }

    public Task(String title, String description, Date creationDate, Date dueDate, Boolean completed) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.completed = completed;
    }

}

package com.demo.entity;
import com.demo.convert.LocalDateTimeAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private Boolean completed = false;

    @ManyToMany(fetch = FetchType.EAGER)
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

    public Task(String title, String description, Date creationDate, LocalDateTime dueDate, User assignedTo , User createdBy) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
        this.createdBy = createdBy;
    }

}

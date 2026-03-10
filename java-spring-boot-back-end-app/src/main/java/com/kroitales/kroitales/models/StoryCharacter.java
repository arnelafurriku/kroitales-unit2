package com.kroitales.kroitales.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "characters")
public class StoryCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public StoryCharacter() {}

    public StoryCharacter(String name, String traits) {
        this.name = name;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
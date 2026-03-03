package com.kroitales.kroitales.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sidekicks")
public class Sidekick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String traits;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Sidekick() {}

    public Sidekick(String name, String traits) {
        this.name = name;
        this.traits = traits;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTraits() { return traits; }
    public void setTraits(String traits) { this.traits = traits; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
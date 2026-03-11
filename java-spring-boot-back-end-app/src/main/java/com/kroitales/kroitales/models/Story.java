package com.kroitales.kroitales.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "notes_tags", columnDefinition = "TEXT")
    private String notesTags;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private StoryCharacter character;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sidekick_id", nullable = false)
    private Sidekick sidekick;

    @ManyToOne(optional = false)
    @JoinColumn(name = "setting_id", nullable = false)
    private Setting setting;

    @ManyToOne(optional = false)
    @JoinColumn(name = "action_id", nullable = false)
    private Action action;
}
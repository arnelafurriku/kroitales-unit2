package com.kroitales.kroitales.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    public Story() {}

    public Story(String title, String content, String notesTags,
                 StoryCharacter character, Sidekick sidekick, Setting setting, Action action) {
        this.title = title;
        this.content = content;
        this.notesTags = notesTags;
        this.character = character;
        this.sidekick = sidekick;
        this.setting = setting;
        this.action = action;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getNotesTags() { return notesTags; }
    public void setNotesTags(String notesTags) { this.notesTags = notesTags; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public StoryCharacter getCharacter() { return character; }
    public void setCharacter(StoryCharacter character) { this.character = character; }

    public Sidekick getSidekick() { return sidekick; }
    public void setSidekick(Sidekick sidekick) { this.sidekick = sidekick; }

    public Setting getSetting() { return setting; }
    public void setSetting(Setting setting) { this.setting = setting; }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }
}
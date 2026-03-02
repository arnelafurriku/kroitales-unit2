package com.kroitales.kroitales.dto;

import jakarta.validation.constraints.NotNull;

public class StoryCreateRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String notesTags;

    @NotNull
    private Long characterId;

    @NotNull
    private Long sidekickId;

    @NotNull
    private Long settingId;

    @NotNull
    private Long actionId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getNotesTags() { return notesTags; }
    public void setNotesTags(String notesTags) { this.notesTags = notesTags; }

    public Long getCharacterId() { return characterId; }
    public void setCharacterId(Long characterId) { this.characterId = characterId; }

    public Long getSidekickId() { return sidekickId; }
    public void setSidekickId(Long sidekickId) { this.sidekickId = sidekickId; }

    public Long getSettingId() { return settingId; }
    public void setSettingId(Long settingId) { this.settingId = settingId; }

    public Long getActionId() { return actionId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }
}
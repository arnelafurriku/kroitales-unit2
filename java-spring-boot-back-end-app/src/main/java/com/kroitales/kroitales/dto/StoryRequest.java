package com.kroitales.kroitales.dto;

public class StoryRequest {

    private String title;
    private String content;
    private String notesTags;

    private Long characterId;
    private Long sidekickId;
    private Long settingId;
    private Long actionId;

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getNotesTags() { return notesTags; }

    public Long getCharacterId() { return characterId; }
    public Long getSidekickId() { return sidekickId; }
    public Long getSettingId() { return settingId; }
    public Long getActionId() { return actionId; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setNotesTags(String notesTags) { this.notesTags = notesTags; }

    public void setCharacterId(Long characterId) { this.characterId = characterId; }
    public void setSidekickId(Long sidekickId) { this.sidekickId = sidekickId; }
    public void setSettingId(Long settingId) { this.settingId = settingId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }
}
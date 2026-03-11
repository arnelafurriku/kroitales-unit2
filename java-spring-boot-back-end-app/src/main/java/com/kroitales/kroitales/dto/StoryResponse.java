package com.kroitales.kroitales.dto;

import java.time.LocalDateTime;

public class StoryResponse {

    private Long id;
    private String title;
    private String notes;
    private String content;

    private Long characterId;
    private String characterName;

    private Long sidekickId;
    private String sidekickName;

    private Long settingId;
    private String settingName;

    private Long actionId;
    private String actionName;

    private LocalDateTime createdAt;

    public StoryResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Long getSidekickId() {
        return sidekickId;
    }

    public void setSidekickId(Long sidekickId) {
        this.sidekickId = sidekickId;
    }

    public String getSidekickName() {
        return sidekickName;
    }

    public void setSidekickName(String sidekickName) {
        this.sidekickName = sidekickName;
    }

    public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
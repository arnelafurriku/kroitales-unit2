package com.kroitales.kroitales.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryResponse {
    private Long id;
    private String title;
    private String content;
    private String notes;

    private Long characterId;
    private String characterName;

    private Long sidekickId;
    private String sidekickName;

    private Long settingId;
    private String settingName;

    private Long actionId;
    private String actionName;

    private LocalDateTime createdAt;
}
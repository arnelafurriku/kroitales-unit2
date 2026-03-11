package com.kroitales.kroitales.response;

import lombok.Data;

@Data
public class StoryResponse {
    private Long id;
    private String title;
    private String content;
    private String notesTags;

    private Long characterId;
    private String characterName;

    private Long sidekickId;
    private String sidekickName;

    private Long settingId;
    private String settingName;

    private Long actionId;
    private String actionName;
}
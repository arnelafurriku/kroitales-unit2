package com.kroitales.kroitales.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoryCreateRequest {

    @NotBlank
    private String title;

    private String content;

    private String notes;

    @NotNull
    private Long characterId;

    @NotNull
    private Long sidekickId;

    @NotNull
    private Long settingId;

    @NotNull
    private Long actionId;
}
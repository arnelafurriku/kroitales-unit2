package com.kroitales.kroitales.mapper;

import com.kroitales.kroitales.dto.StoryCreateRequest;
import com.kroitales.kroitales.models.Story;
import com.kroitales.kroitales.response.StoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "character", ignore = true)
    @Mapping(target = "sidekick", ignore = true)
    @Mapping(target = "setting", ignore = true)
    @Mapping(target = "action", ignore = true)
    @Mapping(target = "notes", source = "notes")
    Story toEntity(StoryCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "character", ignore = true)
    @Mapping(target = "sidekick", ignore = true)
    @Mapping(target = "setting", ignore = true)
    @Mapping(target = "action", ignore = true)
    @Mapping(target = "notes", source = "notes")
    void updateStoryFromRequest(StoryCreateRequest request, @MappingTarget Story story);

    @Mapping(target = "notes", source = "notes")
    @Mapping(target = "characterId", source = "character.id")
    @Mapping(target = "characterName", source = "character.name")
    @Mapping(target = "sidekickId", source = "sidekick.id")
    @Mapping(target = "sidekickName", source = "sidekick.name")
    @Mapping(target = "settingId", source = "setting.id")
    @Mapping(target = "settingName", source = "setting.name")
    @Mapping(target = "actionId", source = "action.id")
    @Mapping(target = "actionName", source = "action.name")
    StoryResponse toResponse(Story story);
}
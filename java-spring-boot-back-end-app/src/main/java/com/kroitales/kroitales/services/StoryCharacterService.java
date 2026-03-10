package com.kroitales.kroitales.services;

import com.kroitales.kroitales.models.StoryCharacter;

import java.util.List;

public interface StoryCharacterService {
    List<StoryCharacter> getAllCharacters();
    StoryCharacter getCharacterById(Long id);
    StoryCharacter createCharacter(StoryCharacter character);
    StoryCharacter updateCharacter(Long id, StoryCharacter updatedCharacter);
    void deleteCharacter(Long id);
}
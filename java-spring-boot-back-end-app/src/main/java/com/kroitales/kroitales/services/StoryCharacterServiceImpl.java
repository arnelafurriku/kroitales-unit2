package com.kroitales.kroitales.services;

import com.kroitales.kroitales.data.StoryCharacterRepository;
import com.kroitales.kroitales.models.StoryCharacter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryCharacterServiceImpl implements StoryCharacterService {

    private final StoryCharacterRepository characterRepository;

    public StoryCharacterServiceImpl(StoryCharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public List<StoryCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }

    @Override
    public StoryCharacter getCharacterById(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Character not found with id: " + id));
    }

    @Override
    public StoryCharacter createCharacter(StoryCharacter character) {
        return characterRepository.save(character);
    }

    @Override
    public StoryCharacter updateCharacter(Long id, StoryCharacter updatedCharacter) {
        StoryCharacter existing = getCharacterById(id);
        existing.setName(updatedCharacter.getName());
        return characterRepository.save(existing);
    }

    @Override
    public void deleteCharacter(Long id) {
        StoryCharacter existing = getCharacterById(id);
        characterRepository.delete(existing);
    }
}
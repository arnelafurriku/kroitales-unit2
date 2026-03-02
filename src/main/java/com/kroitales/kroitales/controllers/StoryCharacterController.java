package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.data.StoryCharacterRepository;
import com.kroitales.kroitales.models.StoryCharacter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryCharacterController {

    private final StoryCharacterRepository characterRepository;

    public StoryCharacterController(StoryCharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    // ✅ GET all characters
    @GetMapping
    public List<StoryCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }

    // ✅ GET by id
    @GetMapping("/{id}")
    public ResponseEntity<StoryCharacter> getById(@PathVariable Long id) {
        return characterRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST create character
    @PostMapping
    public StoryCharacter createCharacter(@RequestBody @Valid StoryCharacter character) {
        return characterRepository.save(character);
    }
}
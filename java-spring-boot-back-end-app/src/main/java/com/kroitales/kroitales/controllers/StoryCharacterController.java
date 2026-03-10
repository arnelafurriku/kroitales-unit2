package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.models.StoryCharacter;
import com.kroitales.kroitales.services.StoryCharacterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryCharacterController {

    private final StoryCharacterService characterService;

    public StoryCharacterController(StoryCharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public List<StoryCharacter> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryCharacter> getById(@PathVariable Long id) {
        return ResponseEntity.ok(characterService.getCharacterById(id));
    }

    @PostMapping
    public ResponseEntity<StoryCharacter> createCharacter(@RequestBody @Valid StoryCharacter character) {
        return ResponseEntity.ok(characterService.createCharacter(character));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryCharacter> updateCharacter(
            @PathVariable Long id,
            @RequestBody @Valid StoryCharacter character
    ) {
        return ResponseEntity.ok(characterService.updateCharacter(id, character));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }
}
package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.data.*;
import com.kroitales.kroitales.models.*;
import com.kroitales.kroitales.dto.StoryCreateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController {

    private final StoryRepository storyRepository;
    private final StoryCharacterRepository characterRepository;
    private final SidekickRepository sidekickRepository;
    private final SettingRepository settingRepository;
    private final ActionRepository actionRepository;

    public StoryController(
            StoryRepository storyRepository,
            StoryCharacterRepository characterRepository,
            SidekickRepository sidekickRepository,
            SettingRepository settingRepository,
            ActionRepository actionRepository
    ) {
        this.storyRepository = storyRepository;
        this.characterRepository = characterRepository;
        this.sidekickRepository = sidekickRepository;
        this.settingRepository = settingRepository;
        this.actionRepository = actionRepository;
    }

    // GET all
    @GetMapping
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    // GET by id
    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable Long id) {
        return storyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create story
    @PostMapping
    public ResponseEntity<?> createStory(@RequestBody @Valid StoryCreateRequest req) {

        Optional<StoryCharacter> characterOpt = characterRepository.findById(req.getCharacterId());
        Optional<Sidekick> sidekickOpt = sidekickRepository.findById(req.getSidekickId());
        Optional<Setting> settingOpt = settingRepository.findById(req.getSettingId());
        Optional<com.kroitales.kroitales.models.Action> actionOpt = actionRepository.findById(req.getActionId());

        if (characterOpt.isEmpty() || sidekickOpt.isEmpty() || settingOpt.isEmpty() || actionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("One or more related IDs do not exist.");
        }

        Story story = new Story(
                req.getTitle(),
                req.getContent(),
                req.getNotesTags(),
                characterOpt.get(),
                sidekickOpt.get(),
                settingOpt.get(),
                actionOpt.get()
        );

        return ResponseEntity.ok(storyRepository.save(story));
    }

    // ✅ PUT update story
    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable Long id, @RequestBody @Valid StoryCreateRequest req) {

        return storyRepository.findById(id).map(existing -> {

            existing.setTitle(req.getTitle());
            existing.setContent(req.getContent());
            existing.setNotesTags(req.getNotesTags());

            existing.setCharacter(characterRepository.findById(req.getCharacterId()).orElse(existing.getCharacter()));
            existing.setSidekick(sidekickRepository.findById(req.getSidekickId()).orElse(existing.getSidekick()));
            existing.setSetting(settingRepository.findById(req.getSettingId()).orElse(existing.getSetting()));
            existing.setAction(actionRepository.findById(req.getActionId()).orElse(existing.getAction()));

            return ResponseEntity.ok(storyRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        if (!storyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        storyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
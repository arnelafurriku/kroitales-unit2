package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.data.ActionRepository;
import com.kroitales.kroitales.data.SettingRepository;
import com.kroitales.kroitales.data.SidekickRepository;
import com.kroitales.kroitales.data.StoryCharacterRepository;
import com.kroitales.kroitales.data.StoryRepository;
import com.kroitales.kroitales.dto.StoryRequest;
import com.kroitales.kroitales.models.Action;
import com.kroitales.kroitales.models.Setting;
import com.kroitales.kroitales.models.Sidekick;
import com.kroitales.kroitales.models.Story;
import com.kroitales.kroitales.models.StoryCharacter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController {

    private final StoryRepository storyRepository;
    private final StoryCharacterRepository storyCharacterRepository;
    private final SidekickRepository sidekickRepository;
    private final SettingRepository settingRepository;
    private final ActionRepository actionRepository;

    public StoryController(
            StoryRepository storyRepository,
            StoryCharacterRepository storyCharacterRepository,
            SidekickRepository sidekickRepository,
            SettingRepository settingRepository,
            ActionRepository actionRepository
    ) {
        this.storyRepository = storyRepository;
        this.storyCharacterRepository = storyCharacterRepository;
        this.sidekickRepository = sidekickRepository;
        this.settingRepository = settingRepository;
        this.actionRepository = actionRepository;
    }

    @GetMapping
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody StoryRequest request) {
        StoryCharacter character = storyCharacterRepository.findById(request.getCharacterId())
                .orElseThrow(() -> new RuntimeException("Character not found"));

        Sidekick sidekick = sidekickRepository.findById(request.getSidekickId())
                .orElseThrow(() -> new RuntimeException("Sidekick not found"));

        Setting setting = settingRepository.findById(request.getSettingId())
                .orElseThrow(() -> new RuntimeException("Setting not found"));

        Action action = actionRepository.findById(request.getActionId())
                .orElseThrow(() -> new RuntimeException("Action not found"));

        Story story = new Story(
                request.getTitle(),
                request.getContent(),
                request.getNotesTags(),
                character,
                sidekick,
                setting,
                action
        );

        Story savedStory = storyRepository.save(story);

        return new ResponseEntity<>(savedStory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        if (!storyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        storyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable Long id, @RequestBody StoryRequest request) {
        System.out.println("PUT /api/stories/" + id + " called");
        System.out.println("Title: " + request.getTitle());
        System.out.println("Content: " + request.getContent());
        System.out.println("CharacterId: " + request.getCharacterId());
        System.out.println("SidekickId: " + request.getSidekickId());
        System.out.println("SettingId: " + request.getSettingId());
        System.out.println("ActionId: " + request.getActionId());

        Story existingStory = storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        StoryCharacter character = storyCharacterRepository.findById(request.getCharacterId())
                .orElseThrow(() -> new RuntimeException("Character not found"));

        Sidekick sidekick = sidekickRepository.findById(request.getSidekickId())
                .orElseThrow(() -> new RuntimeException("Sidekick not found"));

        Setting setting = settingRepository.findById(request.getSettingId())
                .orElseThrow(() -> new RuntimeException("Setting not found"));

        Action action = actionRepository.findById(request.getActionId())
                .orElseThrow(() -> new RuntimeException("Action not found"));

        existingStory.setTitle(request.getTitle());
        existingStory.setContent(request.getContent());
        existingStory.setNotesTags(request.getNotesTags());
        existingStory.setCharacter(character);
        existingStory.setSidekick(sidekick);
        existingStory.setSetting(setting);
        existingStory.setAction(action);

        Story savedStory = storyRepository.save(existingStory);
        System.out.println("Saved story id: " + savedStory.getId());
        System.out.println("Saved story content: " + savedStory.getContent());

        return ResponseEntity.ok(savedStory);
    }
}
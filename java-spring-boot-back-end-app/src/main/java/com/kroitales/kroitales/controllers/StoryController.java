package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.dto.StoryCreateRequest;
import com.kroitales.kroitales.models.Story;
import com.kroitales.kroitales.services.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public List<Story> getAllStories() {
        return storyService.getAllStories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.getStoryById(id));
    }

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody @Valid StoryCreateRequest request) {
        return ResponseEntity.ok(storyService.createStory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(
            @PathVariable Long id,
            @RequestBody @Valid StoryCreateRequest request
    ) {
        return ResponseEntity.ok(storyService.updateStory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }
}
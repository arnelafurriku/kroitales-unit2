package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.dto.StoryCreateRequest;
import com.kroitales.kroitales.response.StoryResponse;
import com.kroitales.kroitales.services.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @GetMapping
    public ResponseEntity<List<StoryResponse>> getAllStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponse> getStoryById(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.getStoryById(id));
    }

    @PostMapping
    public ResponseEntity<StoryResponse> createStory(@RequestBody @Valid StoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storyService.createStory(request));
    }

    @PostMapping("/generate")
    public ResponseEntity<StoryResponse> generateStory(@RequestBody @Valid StoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storyService.generateAndSaveStory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryResponse> updateStory(
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
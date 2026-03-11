package com.kroitales.kroitales.services;

import com.kroitales.kroitales.dto.StoryCreateRequest;
import com.kroitales.kroitales.response.StoryResponse;

import java.util.List;

public interface StoryService {
    List<StoryResponse> getAllStories();

    StoryResponse getStoryById(Long id);

    StoryResponse createStory(StoryCreateRequest request);

    StoryResponse generateAndSaveStory(StoryCreateRequest request);

    StoryResponse updateStory(Long id, StoryCreateRequest request);

    void deleteStory(Long id);
}
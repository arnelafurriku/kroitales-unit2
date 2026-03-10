package com.kroitales.kroitales.services;

import com.kroitales.kroitales.dto.StoryCreateRequest;
import com.kroitales.kroitales.models.Story;

import java.util.List;

public interface StoryService {
    List<Story> getAllStories();
    Story getStoryById(Long id);
    Story createStory(StoryCreateRequest request);
    Story updateStory(Long id, StoryCreateRequest request);
    void deleteStory(Long id);
}
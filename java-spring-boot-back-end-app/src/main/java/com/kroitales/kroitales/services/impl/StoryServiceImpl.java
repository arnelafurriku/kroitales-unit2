package com.kroitales.kroitales.services.impl;

import com.kroitales.kroitales.data.ActionRepository;
import com.kroitales.kroitales.data.SettingRepository;
import com.kroitales.kroitales.data.SidekickRepository;
import com.kroitales.kroitales.data.StoryCharacterRepository;
import com.kroitales.kroitales.data.StoryRepository;
import com.kroitales.kroitales.dto.StoryCreateRequest;
import com.kroitales.kroitales.exceptions.ResourceNotFoundException;
import com.kroitales.kroitales.mapper.StoryMapper;
import com.kroitales.kroitales.models.Action;
import com.kroitales.kroitales.models.Setting;
import com.kroitales.kroitales.models.Sidekick;
import com.kroitales.kroitales.models.Story;
import com.kroitales.kroitales.models.StoryCharacter;
import com.kroitales.kroitales.response.StoryResponse;
import com.kroitales.kroitales.services.GeminiStoryService;
import com.kroitales.kroitales.services.PromptBuilderService;
import com.kroitales.kroitales.services.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final StoryCharacterRepository characterRepository;
    private final SidekickRepository sidekickRepository;
    private final SettingRepository settingRepository;
    private final ActionRepository actionRepository;
    private final StoryMapper storyMapper;
    private final PromptBuilderService promptBuilderService;
    private final GeminiStoryService geminiStoryService;

    @Override
    public List<StoryResponse> getAllStories() {
        return storyRepository.findAll()
                .stream()
                .map(storyMapper::toResponse)
                .toList();
    }

    @Override
    public StoryResponse getStoryById(Long id) {
        return storyMapper.toResponse(requestStory(id));
    }

    @Override
    public StoryResponse createStory(StoryCreateRequest request) {
        Story story = storyMapper.toEntity(request);

        story.setCharacter(requestCharacter(request.getCharacterId()));
        story.setSidekick(requestSidekick(request.getSidekickId()));
        story.setSetting(requestSetting(request.getSettingId()));
        story.setAction(requestAction(request.getActionId()));

        return storyMapper.toResponse(storyRepository.save(story));
    }

    @Override
    public StoryResponse generateAndSaveStory(StoryCreateRequest request) {
        StoryCharacter character = requestCharacter(request.getCharacterId());
        Sidekick sidekick = requestSidekick(request.getSidekickId());
        Setting setting = requestSetting(request.getSettingId());
        Action action = requestAction(request.getActionId());

        String prompt = promptBuilderService.buildPrompt(
                character,
                sidekick,
                setting,
                action,
                request.getNotes()
        );

        String generatedContent = geminiStoryService.generateStory(prompt);

        Story story = storyMapper.toEntity(request);
        story.setCharacter(character);
        story.setSidekick(sidekick);
        story.setSetting(setting);
        story.setAction(action);
        story.setContent(generatedContent);

        return storyMapper.toResponse(storyRepository.save(story));
    }

    @Override
    public StoryResponse updateStory(Long id, StoryCreateRequest request) {
        Story story = requestStory(id);

        storyMapper.updateStoryFromRequest(request, story);
        story.setCharacter(requestCharacter(request.getCharacterId()));
        story.setSidekick(requestSidekick(request.getSidekickId()));
        story.setSetting(requestSetting(request.getSettingId()));
        story.setAction(requestAction(request.getActionId()));

        return storyMapper.toResponse(storyRepository.save(story));
    }

    @Override
    public void deleteStory(Long id) {
        storyRepository.delete(requestStory(id));
    }

    private Story requestStory(Long id) {
        return storyRepository.findById(id)
                .orElseThrow(() -> notFoundException("Story", id));
    }

    private StoryCharacter requestCharacter(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(() -> notFoundException("Character", id));
    }

    private Sidekick requestSidekick(Long id) {
        return sidekickRepository.findById(id)
                .orElseThrow(() -> notFoundException("Sidekick", id));
    }

    private Setting requestSetting(Long id) {
        return settingRepository.findById(id)
                .orElseThrow(() -> notFoundException("Setting", id));
    }

    private Action requestAction(Long id) {
        return actionRepository.findById(id)
                .orElseThrow(() -> notFoundException("Action", id));
    }

    private ResourceNotFoundException notFoundException(String resourceName, Long id) {
        return new ResourceNotFoundException(resourceName + " not found with id: " + id);
    }
}
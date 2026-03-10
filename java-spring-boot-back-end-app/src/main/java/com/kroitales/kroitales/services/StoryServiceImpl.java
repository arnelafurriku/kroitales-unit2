package com.kroitales.kroitales.services;

import com.kroitales.kroitales.data.ActionRepository;
import com.kroitales.kroitales.data.SettingRepository;
import com.kroitales.kroitales.data.SidekickRepository;
import com.kroitales.kroitales.data.StoryCharacterRepository;
import com.kroitales.kroitales.data.StoryRepository;
import com.kroitales.kroitales.dto.StoryCreateRequest;
import com.kroitales.kroitales.models.Action;
import com.kroitales.kroitales.models.Setting;
import com.kroitales.kroitales.models.Sidekick;
import com.kroitales.kroitales.models.Story;
import com.kroitales.kroitales.models.StoryCharacter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final StoryCharacterRepository characterRepository;
    private final SidekickRepository sidekickRepository;
    private final SettingRepository settingRepository;
    private final ActionRepository actionRepository;

    public StoryServiceImpl(
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

    @Override
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    @Override
    public Story getStoryById(Long id) {
        return storyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Story not found with id: " + id));
    }

    @Override
    public Story createStory(StoryCreateRequest request) {
        StoryCharacter character = characterRepository.findById(request.getCharacterId())
                .orElseThrow(() -> new EntityNotFoundException("Character not found with id: " + request.getCharacterId()));

        Sidekick sidekick = sidekickRepository.findById(request.getSidekickId())
                .orElseThrow(() -> new EntityNotFoundException("Sidekick not found with id: " + request.getSidekickId()));

        Setting setting = settingRepository.findById(request.getSettingId())
                .orElseThrow(() -> new EntityNotFoundException("Setting not found with id: " + request.getSettingId()));

        Action action = actionRepository.findById(request.getActionId())
                .orElseThrow(() -> new EntityNotFoundException("Action not found with id: " + request.getActionId()));

        Story story = new Story();
        story.setTitle(request.getTitle());
        story.setContent(request.getContent());
        story.setCharacter(character);
        story.setSidekick(sidekick);
        story.setSetting(setting);
        story.setAction(action);

        return storyRepository.save(story);
    }

    @Override
    public Story updateStory(Long id, StoryCreateRequest request) {
        Story existingStory = getStoryById(id);

        StoryCharacter character = characterRepository.findById(request.getCharacterId())
                .orElseThrow(() -> new EntityNotFoundException("Character not found with id: " + request.getCharacterId()));

        Sidekick sidekick = sidekickRepository.findById(request.getSidekickId())
                .orElseThrow(() -> new EntityNotFoundException("Sidekick not found with id: " + request.getSidekickId()));

        Setting setting = settingRepository.findById(request.getSettingId())
                .orElseThrow(() -> new EntityNotFoundException("Setting not found with id: " + request.getSettingId()));

        Action action = actionRepository.findById(request.getActionId())
                .orElseThrow(() -> new EntityNotFoundException("Action not found with id: " + request.getActionId()));

        existingStory.setTitle(request.getTitle());
        existingStory.setContent(request.getContent());
        existingStory.setCharacter(character);
        existingStory.setSidekick(sidekick);
        existingStory.setSetting(setting);
        existingStory.setAction(action);

        return storyRepository.save(existingStory);
    }

    @Override
    public void deleteStory(Long id) {
        Story existingStory = getStoryById(id);
        storyRepository.delete(existingStory);
    }
}
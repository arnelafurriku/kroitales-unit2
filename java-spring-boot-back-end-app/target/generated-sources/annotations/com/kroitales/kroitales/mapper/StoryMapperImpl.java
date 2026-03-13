package com.kroitales.kroitales.mapper;

import com.kroitales.kroitales.dto.StoryCreateRequest;
import com.kroitales.kroitales.models.Action;
import com.kroitales.kroitales.models.Setting;
import com.kroitales.kroitales.models.Sidekick;
import com.kroitales.kroitales.models.Story;
import com.kroitales.kroitales.models.StoryCharacter;
import com.kroitales.kroitales.response.StoryResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-12T22:27:24-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Microsoft)"
)
@Component
public class StoryMapperImpl implements StoryMapper {

    @Override
    public Story toEntity(StoryCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Story story = new Story();

        story.setNotes( request.getNotes() );
        story.setTitle( request.getTitle() );
        story.setContent( request.getContent() );

        return story;
    }

    @Override
    public void updateStoryFromRequest(StoryCreateRequest request, Story story) {
        if ( request == null ) {
            return;
        }

        story.setNotes( request.getNotes() );
        story.setTitle( request.getTitle() );
        story.setContent( request.getContent() );
    }

    @Override
    public StoryResponse toResponse(Story story) {
        if ( story == null ) {
            return null;
        }

        StoryResponse storyResponse = new StoryResponse();

        storyResponse.setNotes( story.getNotes() );
        storyResponse.setCharacterId( storyCharacterId( story ) );
        storyResponse.setCharacterName( storyCharacterName( story ) );
        storyResponse.setSidekickId( storySidekickId( story ) );
        storyResponse.setSidekickName( storySidekickName( story ) );
        storyResponse.setSettingId( storySettingId( story ) );
        storyResponse.setSettingName( storySettingName( story ) );
        storyResponse.setActionId( storyActionId( story ) );
        storyResponse.setActionName( storyActionName( story ) );
        storyResponse.setId( story.getId() );
        storyResponse.setTitle( story.getTitle() );
        storyResponse.setContent( story.getContent() );
        storyResponse.setCreatedAt( story.getCreatedAt() );

        return storyResponse;
    }

    private Long storyCharacterId(Story story) {
        if ( story == null ) {
            return null;
        }
        StoryCharacter character = story.getCharacter();
        if ( character == null ) {
            return null;
        }
        Long id = character.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String storyCharacterName(Story story) {
        if ( story == null ) {
            return null;
        }
        StoryCharacter character = story.getCharacter();
        if ( character == null ) {
            return null;
        }
        String name = character.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long storySidekickId(Story story) {
        if ( story == null ) {
            return null;
        }
        Sidekick sidekick = story.getSidekick();
        if ( sidekick == null ) {
            return null;
        }
        Long id = sidekick.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String storySidekickName(Story story) {
        if ( story == null ) {
            return null;
        }
        Sidekick sidekick = story.getSidekick();
        if ( sidekick == null ) {
            return null;
        }
        String name = sidekick.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long storySettingId(Story story) {
        if ( story == null ) {
            return null;
        }
        Setting setting = story.getSetting();
        if ( setting == null ) {
            return null;
        }
        Long id = setting.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String storySettingName(Story story) {
        if ( story == null ) {
            return null;
        }
        Setting setting = story.getSetting();
        if ( setting == null ) {
            return null;
        }
        String name = setting.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long storyActionId(Story story) {
        if ( story == null ) {
            return null;
        }
        Action action = story.getAction();
        if ( action == null ) {
            return null;
        }
        Long id = action.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String storyActionName(Story story) {
        if ( story == null ) {
            return null;
        }
        Action action = story.getAction();
        if ( action == null ) {
            return null;
        }
        String name = action.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}

package com.kroitales.kroitales.services;

import com.kroitales.kroitales.models.Action;
import com.kroitales.kroitales.models.Setting;
import com.kroitales.kroitales.models.Sidekick;
import com.kroitales.kroitales.models.StoryCharacter;
import org.springframework.stereotype.Service;

@Service
public class PromptBuilderService {

    public String buildPrompt(
            StoryCharacter character,
            Sidekick sidekick,
            Setting setting,
            Action action,
            String notes
    ) {
        String safeNotes = notes == null ? "" : notes.trim();

        return """
                Create a short bedtime story for a young child.

                Main character: %s
                Sidekick: %s
                Setting: %s
                Main action: %s
                Extra notes: %s

                Rules:
                - Keep the tone warm, gentle, and child-friendly
                - No scary, violent, or inappropriate content
                - Use simple vocabulary for young children
                - Keep the story between 120 and 160 words
                - Give it a clear beginning, middle, and ending
                - End with a calm bedtime feeling
                - Return only the story text
                """.formatted(
                character.getName(),
                sidekick.getName(),
                setting.getName(),
                action.getName(),
                safeNotes
        );
    }
}
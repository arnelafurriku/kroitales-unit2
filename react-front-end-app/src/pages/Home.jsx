import { useState, useEffect } from "react";
import Builder from "../components/Builder.jsx";
import Library from "../components/Library.jsx";
import { createCharacter, updateCharacter } from "../api/charactersApi";
import { createSidekick, updateSidekick } from "../api/sidekicksApi";
import { createSetting, updateSetting } from "../api/settingsApi";
import { createAction, updateAction } from "../api/actionsApi";
import {
  createStory,
  getAllStories,
  updateStory,
  deleteStory,
  generateStory,
} from "../api/storiesApi";

const INITIAL_BUILDER_STATE = {
  character: "",
  sidekick: "",
  setting: "",
  action: "",
  notes: "",
  title: "",
};

function buildStory({ character, sidekick, setting, action, notes }) {
  const cleanCharacter = character.trim();
  const cleanSidekick = sidekick.trim();
  const cleanSetting = setting.trim();
  const cleanAction = action.trim();
  const cleanNotes = notes.trim();

  if (!cleanCharacter || !cleanSidekick || !cleanSetting || !cleanAction) {
    return "";
  }

  return `Once upon a time in a ${cleanSetting}, ${cleanCharacter} and ${cleanSidekick} went on an adventure to ${cleanAction}. ${
    cleanNotes ? `Along the way, they kept thinking about ${cleanNotes}. ` : ""
  }At the end of the day, they felt happy, safe, and proud.`;
}

function mapStoryFromApi(story) {
  return {
    id: story.id,
    title: story.title || "Untitled story",
    text: story.content || "",
    notes: story.notes || "",
    character: story.characterName || "",
    sidekick: story.sidekickName || "",
    setting: story.settingName || "",
    action: story.actionName || "",
    characterId: story.characterId || null,
    sidekickId: story.sidekickId || null,
    settingId: story.settingId || null,
    actionId: story.actionId || null,
    createdAt: story.createdAt || "",
  };
}

function escapeHtml(text = "") {
  return text
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}

function buildPrintableStoryHtml(story) {
  const title = escapeHtml(story.title || "Untitled story");
  const content = escapeHtml(story.text || "");
  const createdAt = story.createdAt
    ? new Date(story.createdAt).toLocaleDateString()
    : "—";

  return `
    <html>
      <head>
        <title>${title}</title>
        <style>
          body {
            font-family: Arial, sans-serif;
            background: #ffffff;
            color: #222;
            margin: 0;
            padding: 32px;
            line-height: 1.6;
          }

          .print-page {
            max-width: 800px;
            margin: 0 auto;
          }

          .print-title {
            text-align: center;
            margin-bottom: 8px;
            font-size: 28px;
          }

          .print-date {
            text-align: center;
            margin-bottom: 24px;
            color: #666;
            font-size: 14px;
          }

          .print-meta {
            margin-bottom: 24px;
            padding: 16px;
            border: 1px solid #ddd;
            border-radius: 8px;
          }

          .print-meta p {
            margin: 6px 0;
          }

          .print-section-title {
            font-size: 18px;
            margin-bottom: 10px;
          }

          .print-story,
          .print-notes {
            white-space: pre-wrap;
          }

          @media print {
            body {
              padding: 20px;
            }

            .print-page {
              max-width: 100%;
            }
          }
        </style>
      </head>
      <body>
        <div class="print-page">
          <h1 class="print-title">${title}</h1>
          <div class="print-date">Created: ${createdAt}</div>
          <div class="print-section">
            <h2 class="print-section-title">Story</h2>
            <div class="print-story">${content || "No story content available."}</div>
          </div>
        </div>
      </body>
    </html>
  `;
}

function Home() {
  const [builderState, setBuilderState] = useState(INITIAL_BUILDER_STATE);
  const [storyText, setStoryText] = useState("");
  const [savedStories, setSavedStories] = useState([]);
  const [isGenerating, setIsGenerating] = useState(false);
  const [editingStoryId, setEditingStoryId] = useState(null);
  const [selectedStoryId, setSelectedStoryId] = useState(null);

  useEffect(() => {
    loadStories();
  }, []);

  async function loadStories() {
    try {
      const stories = await getAllStories();
      setSavedStories(stories.map(mapStoryFromApi));
    } catch (error) {
      console.error("Failed to load stories:", error);
    }
  }

  function resetBuilder() {
  setBuilderState(INITIAL_BUILDER_STATE);
  setStoryText("");
  setEditingStoryId(null);
  setSelectedStoryId(null);
}

  function handleFieldChange(field, value) {
    setBuilderState((prev) => ({
      ...prev,
      [field]: value,
    }));
  }

  function handleStoryTextChange(value) {
    setStoryText(value);
  }

  async function handleGenerateStory() {
  const characterName = builderState.character.trim();
  const sidekickName = builderState.sidekick.trim();
  const settingName = builderState.setting.trim();
  const actionName = builderState.action.trim();
  const notes = builderState.notes.trim();
  const storyTitle = builderState.title.trim() || "Untitled story";

  if (!characterName || !sidekickName || !settingName || !actionName) {
    alert("Please enter a character, sidekick, setting, and action first.");
    return;
  }

  setIsGenerating(true);
  setStoryText("");

  try {
    let characterId;
    let sidekickId;
    let settingId;
    let actionId;

    if (editingStoryId) {
      const storyBeingEdited = savedStories.find(
        (story) => story.id === editingStoryId
      );

      if (!storyBeingEdited) {
        throw new Error("Story being edited was not found.");
      }

      await updateCharacter(storyBeingEdited.characterId, {
        name: characterName,
      });
      await updateSidekick(storyBeingEdited.sidekickId, {
        name: sidekickName,
      });
      await updateSetting(storyBeingEdited.settingId, {
        name: settingName,
      });
      await updateAction(storyBeingEdited.actionId, {
        name: actionName,
      });

      characterId = storyBeingEdited.characterId;
      sidekickId = storyBeingEdited.sidekickId;
      settingId = storyBeingEdited.settingId;
      actionId = storyBeingEdited.actionId;
    } else {
      const savedCharacter = await createCharacter({ name: characterName });
      const savedSidekick = await createSidekick({ name: sidekickName });
      const savedSetting = await createSetting({ name: settingName });
      const savedAction = await createAction({ name: actionName });

      characterId = savedCharacter.id;
      sidekickId = savedSidekick.id;
      settingId = savedSetting.id;
      actionId = savedAction.id;
    }

    const storyPayload = {
      title: storyTitle,
      notes,
      content: "",
      characterId,
      sidekickId,
      settingId,
      actionId,
    };

    const generatedStory = await generateStory(storyPayload);

    setStoryText(generatedStory.content || "");
    await loadStories();

    if (!editingStoryId) {
      setEditingStoryId(generatedStory.id);
    }
  } catch (error) {
    console.error("Gemini generation failed, using fallback:", error);

    const fallbackText = buildStory(builderState);

    if (!fallbackText) {
      alert(`Failed to generate story: ${error.message}`);
      return;
    }

    setStoryText(fallbackText);
    alert("Gemini generation failed, so the fallback story was used instead.");
  } finally {
    setIsGenerating(false);
  }
}

  async function handleSaveStory() {
  try {
    const characterName = builderState.character.trim();
    const sidekickName = builderState.sidekick.trim();
    const settingName = builderState.setting.trim();
    const actionName = builderState.action.trim();
    const notes = builderState.notes.trim();
    const storyTitle = builderState.title.trim() || "Untitled story";

    if (!characterName || !sidekickName || !settingName || !actionName) {
      alert(
        "Please enter a character, sidekick, setting, and action before saving."
      );
      return;
    }

    let text = storyText.trim();

    if (!text) {
      const generated = buildStory(builderState);

      if (!generated) {
        alert("Please generate a story before saving.");
        return;
      }

      text = generated;
      setStoryText(generated);
    }

    const storyBeingEdited = savedStories.find(
      (story) => story.id === editingStoryId
    );

    if (editingStoryId && storyBeingEdited) {
      await updateCharacter(storyBeingEdited.characterId, {
        name: characterName,
      });

      await updateSidekick(storyBeingEdited.sidekickId, {
        name: sidekickName,
      });

      await updateSetting(storyBeingEdited.settingId, {
        name: settingName,
      });

      await updateAction(storyBeingEdited.actionId, {
        name: actionName,
      });

      const storyPayload = {
        title: storyTitle,
        content: text,
        notes,
        characterId: storyBeingEdited.characterId,
        sidekickId: storyBeingEdited.sidekickId,
        settingId: storyBeingEdited.settingId,
        actionId: storyBeingEdited.actionId,
      };

      await updateStory(editingStoryId, storyPayload);
      alert("Story updated successfully!");
    } else {
      const savedCharacter = await createCharacter({ name: characterName });
      const savedSidekick = await createSidekick({ name: sidekickName });
      const savedSetting = await createSetting({ name: settingName });
      const savedAction = await createAction({ name: actionName });

      const storyPayload = {
        title: storyTitle,
        notes,
        content: text,
        characterId: savedCharacter.id,
        sidekickId: savedSidekick.id,
        settingId: savedSetting.id,
        actionId: savedAction.id,
      };

      const newStory = await createStory(storyPayload);
      setEditingStoryId(newStory.id);
      alert("Story saved successfully!");
    }

    await loadStories();
  } catch (error) {
    console.error("Failed to save story:", error);
    alert(`Something went wrong while saving the story: ${error.message}`);
  }
}

  function handleViewStory(id) {
  const story = savedStories.find((s) => s.id === id);
  if (!story) return;

  setStoryText(story.text);
  setBuilderState({
    character: story.character || "",
    sidekick: story.sidekick || "",
    setting: story.setting || "",
    action: story.action || "",
    notes: story.notes || "",
    title: story.title || "",
  });
  setEditingStoryId(null);
  setSelectedStoryId(id);
}

  function handleEditStory(story) {
  setBuilderState({
    character: story.character || "",
    sidekick: story.sidekick || "",
    setting: story.setting || "",
    action: story.action || "",
    notes: story.notes || "",
    title: story.title || "",
  });

  setStoryText(story.text || "");
  setEditingStoryId(story.id);
  setSelectedStoryId(story.id);
}

async function handleDeleteStory(id) {
  try {
    await deleteStory(id);
    await loadStories();

    if (editingStoryId === id || selectedStoryId === id) {
      resetBuilder();
    }

    alert("Story deleted successfully!");
  } catch (error) {
    console.error("Failed to delete story:", error);
    alert("Something went wrong while deleting the story.");
  }
}

  function handlePrintStory(story) {
    const printWindow = window.open("", "_blank", "width=900,height=700");

    if (!printWindow) {
      alert("Please allow pop-ups to print your story.");
      return;
    }

    const printableHtml = buildPrintableStoryHtml(story);

    printWindow.document.open();
    printWindow.document.write(printableHtml);
    printWindow.document.close();

    printWindow.onload = () => {
      printWindow.focus();
      printWindow.print();
      printWindow.onafterprint = () => printWindow.close();
    };
  }

  function handleReadAloud() {
  const text = storyText.trim();

  if (!text) {
    alert("There is no story to read yet. Generate or load a story first.");
    return;
  }

  if (!("speechSynthesis" in window)) {
    alert("Sorry, Read Aloud is not supported in this browser.");
    return;
  }

  const voices = window.speechSynthesis.getVoices();
  const karenVoice = voices.find((voice) => voice.name === "Karen");

  window.speechSynthesis.cancel();

  const chunks = text
    .split(/\n\s*\n/)
    .map((chunk) => chunk.trim())
    .filter(Boolean);

  chunks.forEach((chunk) => {
    const utterance = new SpeechSynthesisUtterance(chunk);

    if (karenVoice) {
      utterance.voice = karenVoice;
    }

    utterance.lang = "en-US";
    utterance.rate = 0.88;
    utterance.pitch = 1.05;
    utterance.volume = 1;

    window.speechSynthesis.speak(utterance);
  });
}

  function handleStopReadAloud() {
    if (!("speechSynthesis" in window)) return;
    window.speechSynthesis.cancel();
  }

  return (
    <section className="container">
      <header className="home-header">
        <h2 className="home-title">
          Hey Kiddo, Let’s Build Your Story Together!
        </h2>
      </header>

      <div className="builder-grid">
        <Builder
          builderState={builderState}
          storyText={storyText}
          onFieldChange={handleFieldChange}
          onStoryTextChange={handleStoryTextChange}
          onGenerate={handleGenerateStory}
          onSaveStory={handleSaveStory}
          onReadAloud={handleReadAloud}
          onStopReadAloud={handleStopReadAloud}
          isGenerating={isGenerating}
          isEditing={!!editingStoryId}
        />
      </div>

      <Library
        stories={savedStories}
        onViewStory={handleViewStory}
        onEditStory={handleEditStory}
        onDeleteStory={handleDeleteStory}
        onPrintStory={handlePrintStory}
      />
    </section>
  );
}

export default Home;
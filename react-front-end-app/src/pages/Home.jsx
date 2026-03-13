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
} from "../api/storiesApi";

const INITIAL_BUILDER_STATE = {
  character: "",
  sidekick: "",
  setting: "",
  action: "",
  notes: "",
  title: "",
};

function getRandomItem(items) {
  return items[Math.floor(Math.random() * items.length)];
}

function getSmartNoteLines(notes) {
  const lowerNotes = notes.toLowerCase();
  const lines = [];

  if (lowerNotes.includes("bedtime") || lowerNotes.includes("sleep")) {
    lines.push(
      "The adventure felt soft and peaceful, just right for the quiet moments before bedtime."
    );
  }

  if (lowerNotes.includes("moon")) {
    lines.push(
      "Above them, the moon glowed gently, lighting their path like a silver lantern in the sky."
    );
  }

  if (lowerNotes.includes("forest")) {
    lines.push(
      "Tall trees swayed around them, and the forest whispered with secret sounds and gentle wonder."
    );
  }

  if (lowerNotes.includes("stars") || lowerNotes.includes("star")) {
    lines.push(
      "Tiny stars twinkled above them, as if the sky itself was cheering them on."
    );
  }

  if (lowerNotes.includes("magic") || lowerNotes.includes("magical")) {
    lines.push(
      "There was something magical in the air, making every little step feel full of wonder."
    );
  }

  if (lowerNotes.includes("dream") || lowerNotes.includes("dreamy")) {
    lines.push(
      "Everything around them felt dreamy and soft, like a memory waiting to become a bedtime wish."
    );
  }

  if (lowerNotes.includes("calm") || lowerNotes.includes("peaceful")) {
    lines.push(
      "Even when the path felt uncertain, a calm and peaceful feeling stayed with them."
    );
  }

  if (
    lowerNotes.includes("animal") ||
    lowerNotes.includes("bunny") ||
    lowerNotes.includes("bear")
  ) {
    lines.push(
      "Along the way, friendly little animals seemed to appear as if they already knew the adventure was something special."
    );
  }

  return lines;
}

function buildStory({ character, sidekick, setting, action, notes }) {
  const cleanCharacter = character.trim();
  const cleanSidekick = sidekick.trim();
  const cleanSetting = setting.trim();
  const cleanAction = action.trim();
  const cleanNotes = notes.trim();

  if (!cleanCharacter || !cleanSidekick || !cleanSetting || !cleanAction) {
    return "";
  }

  const introOptions = [
    `Once upon a time, in a ${cleanSetting}, ${cleanCharacter} was getting ready to ${cleanAction}.`,
    `One peaceful day in a ${cleanSetting}, ${cleanCharacter} decided it was the perfect time to ${cleanAction}.`,
    `In the magical heart of the ${cleanSetting}, ${cleanCharacter} began a special journey to ${cleanAction}.`,
  ];

  const sidekickOptions = [
    `${cleanCharacter} wasn't alone. Their best friend, ${cleanSidekick}, was right there, ready to help with every brave step.`,
    `Right beside ${cleanCharacter} was ${cleanSidekick}, a loyal companion with a kind heart and a big smile.`,
    `${cleanSidekick} joined the adventure too, bringing courage, laughter, and friendship along the way.`,
  ];

  const middleOptions = [
    `Together, ${cleanCharacter} and ${cleanSidekick} explored the ${cleanSetting}, discovering tiny wonders everywhere they looked.`,
    `As they wandered through the ${cleanSetting}, the two friends noticed sparkling lights, hidden corners, and little magical surprises.`,
    `The ${cleanSetting} felt full of wonder, and ${cleanCharacter} and ${cleanSidekick} explored it with excitement and curiosity.`,
  ];

  const challengeOptions = [
    `Soon, they came across a small challenge, but instead of giving up, they stayed calm and solved it together.`,
    `Along the way, a little problem appeared. But with teamwork, patience, and kindness, they found a way through.`,
    `Every adventure has a tricky moment, and this one did too. Luckily, ${cleanCharacter} and ${cleanSidekick} worked through it bravely.`,
  ];

  const endingOptions = [
    `By the time the stars were blinking sleepily in the sky, ${cleanCharacter} and ${cleanSidekick} had finished their adventure to ${cleanAction}, and they felt proud, cozy, and ready for a good night's sleep.`,
    `When the adventure came to an end, the two friends smiled warmly, knowing they had done something truly special together.`,
    `As nighttime wrapped the ${cleanSetting} in a soft glow, ${cleanCharacter} and ${cleanSidekick} returned home feeling safe, happy, and sleepy.`,
  ];

  const storyParts = [
    getRandomItem(introOptions),
    getRandomItem(sidekickOptions),
    getRandomItem(middleOptions),
  ];

  const smartNoteLines = getSmartNoteLines(cleanNotes);
  storyParts.push(...smartNoteLines);

  if (cleanNotes) {
    storyParts.push(
      `As they continued their journey, they kept thinking about ${cleanNotes}, which made everything feel even more personal and magical.`
    );
  }

  storyParts.push(getRandomItem(challengeOptions));
  storyParts.push(getRandomItem(endingOptions));

  return storyParts.join("\n\n");
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

  function handleGenerateStory() {
    if (
      !builderState.character.trim() ||
      !builderState.sidekick.trim() ||
      !builderState.setting.trim() ||
      !builderState.action.trim()
    ) {
      alert("Please enter a character, sidekick, setting, and action first.");
      return;
    }

    setIsGenerating(true);
    setStoryText("");

    setTimeout(() => {
      const text = buildStory(builderState);
      setStoryText(text);
      setIsGenerating(false);
    }, 600);
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
          notes: notes,
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
          notes: notes,
          content: text,
          characterId: savedCharacter.id,
          sidekickId: savedSidekick.id,
          settingId: savedSetting.id,
          actionId: savedAction.id,
        };

        await createStory(storyPayload);
        alert("Story saved successfully!");
      }

      await loadStories();
      resetBuilder();
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
  }

  async function handleDeleteStory(id) {
    try {
      await deleteStory(id);
      await loadStories();

      if (editingStoryId === id) {
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

    window.speechSynthesis.cancel();

    const utterance = new SpeechSynthesisUtterance(text);
    utterance.rate = 0.95;
    utterance.pitch = 1;
    utterance.lang = "en-US";

    window.speechSynthesis.speak(utterance);
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
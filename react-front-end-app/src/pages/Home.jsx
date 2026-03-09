import { useState, useEffect } from "react";
import Builder from "../components/Builder.jsx";
import Library from "../components/Library.jsx";
import { createCharacter } from "../api/charactersApi";
import { createSidekick } from "../api/sidekicksApi";
import { createSetting } from "../api/settingsApi";
import { createAction } from "../api/actionsApi";
import { createStory } from "../api/storiesApi";

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

function Home() {
  const [builderState, setBuilderState] = useState(INITIAL_BUILDER_STATE);
  const [storyText, setStoryText] = useState("");
  const [savedStories, setSavedStories] = useState([]);
  const [isGenerating, setIsGenerating] = useState(false);

  useEffect(() => {
    try {
      const stored = window.localStorage.getItem("kroiTalesStories");
      if (stored) {
        setSavedStories(JSON.parse(stored));
      }
    } catch (e) {
      console.error("Failed to load saved stories:", e);
    }
  }, []);

  useEffect(() => {
    try {
      window.localStorage.setItem(
        "kroiTalesStories",
        JSON.stringify(savedStories)
      );
    } catch (e) {
      console.error("Failed to persist stories:", e);
    }
  }, [savedStories]);

  function handleFieldChange(field, value) {
    setBuilderState((prev) => ({
      ...prev,
      [field]: value,
    }));
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
      const notesTags = builderState.notes.trim();
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

      const savedCharacter = await createCharacter({
        name: characterName,
      });

      const savedSidekick = await createSidekick({
        name: sidekickName,
      });

      const savedSetting = await createSetting({
        name: settingName,
      });

      const savedAction = await createAction({
        name: actionName,
      });

      const savedStory = await createStory({
        title: storyTitle,
        content: text,
        notesTags,
        characterId: savedCharacter.id,
        sidekickId: savedSidekick.id,
        settingId: savedSetting.id,
        actionId: savedAction.id,
      });

      setSavedStories((prev) => [
        {
          id: savedStory.id.toString(),
          title: savedStory.title,
          text: savedStory.content,
          notes: savedStory.notesTags || "",
          character: characterName,
          sidekick: sidekickName,
          setting: settingName,
          action: actionName,
          createdAt: new Date().toLocaleDateString(),
        },
        ...prev,
      ]);

      setBuilderState({ ...INITIAL_BUILDER_STATE });
      setStoryText("");

      alert("Story saved successfully!");
    } catch (error) {
      console.error("Failed to save story:", error);
      alert("Something went wrong while saving the story.");
    }
  }

  function handleLoadStory(id) {
    const story = savedStories.find((s) => s.id === id);
    if (!story) return;

    setStoryText(story.text || "");
    setBuilderState({
      character: story.character || "",
      sidekick: story.sidekick || "",
      setting: story.setting || "",
      action: story.action || "",
      notes: story.notes || "",
      title: story.title || "",
    });
  }

  function handleEditStory(id) {
    const story = savedStories.find((s) => s.id === id);
    if (!story) return;

    setBuilderState({
      character: story.character || "",
      sidekick: story.sidekick || "",
      setting: story.setting || "",
      action: story.action || "",
      notes: story.notes || "",
      title: story.title || "",
    });

    setStoryText(story.text || "");
  }

  function handleDeleteStory(id) {
    setSavedStories((prev) => prev.filter((story) => story.id !== id));
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
          onGenerate={handleGenerateStory}
          onSaveStory={handleSaveStory}
          onReadAloud={handleReadAloud}
          onStopReadAloud={handleStopReadAloud}
          isGenerating={isGenerating}
        />
      </div>

      <Library
        stories={savedStories}
        onViewStory={handleLoadStory}
        onEditStory={handleEditStory}
        onDeleteStory={handleDeleteStory}
      />
    </section>
  );
}

export default Home;
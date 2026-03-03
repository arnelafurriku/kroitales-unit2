import { useState, useEffect } from "react";
import Builder from "../components/Builder.jsx";
import Library from "../components/Library.jsx";
import { characters, sidekicks, settings, actions } from "../data/stories.js";

const INITIAL_BUILDER_STATE = {
  character: "",
  sidekick: "",
  setting: "",
  action: "",
  notes: "",
  title: "",
};

function buildStory({ character, sidekick, setting, action, notes }) {
  if (!character || !sidekick || !setting || !action) return "";

  const intro = `Once upon a time, in a ${setting}, ${character} was getting ready to ${action}.`;
  const meetSidekick = `${character} wasn't alone. Their best friend, ${sidekick}, was right there, ready to help with every brave step.`;
  const middle = `Together, ${character} and ${sidekick} explored the ${setting}, noticing tiny details that most people never see—sparkling lights, funny shadows, and secret paths that only show up if you really believe in them.`;
  const challenge = `On their way, they faced a little challenge, but instead of giving up, they remembered to be kind, patient, and to listen to each other. That made them even stronger as a team.`;
  const ending = `By the time the stars were blinking sleepily in the sky, ${character} and ${sidekick} had finished their adventure to ${action}, and they felt proud, cozy, and ready for a good night's sleep.`;

  const paragraphs = [intro, meetSidekick, middle, challenge, ending];

  if (notes && notes.trim()) {
    paragraphs.push(`Grown-up note: ${notes.trim()}`);
  }
  return paragraphs.join("\n\n");
}

function Home() {
  const [builderState, setBuilderState] = useState(INITIAL_BUILDER_STATE);
  const [storyText, setStoryText] = useState("");
  const [savedStories, setSavedStories] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);
  const [isGenerating, setIsGenerating] = useState(false);

  useEffect(() => {
    try {
      const stored = window.localStorage.getItem("kroiTalesStories");
      if (stored) {
        setSavedStories(JSON.parse(stored));
      }
    } catch {
      // ignore, start empty
    }
  }, []);

  useEffect(() => {
    try {
      window.localStorage.setItem(
        "kroiTalesStories",
        JSON.stringify(savedStories)
      );
    } catch {
      // ignore, start empty
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
      !builderState.character ||
      !builderState.sidekick ||
      !builderState.setting ||
      !builderState.action
    ) {
      alert("Please pick a character, sidekick, setting and action first.");
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

  function handleSaveStory() {
    let text = storyText.trim();

    if (!text) {
      const generated = buildStory(builderState);
      if (!generated) {
        alert(
          "Please pick a character, sidekick, setting and action before saving."
        );
        return;
      }
      text = generated;
      setStoryText(generated);
    }

    const title = builderState.title.trim() || "Untitled story";

    const newStory = {
      id: Date.now().toString(),
      title,
      text,
      notes: builderState.notes.trim(),
    };

    setSavedStories((prev) => [newStory, ...prev]);

    setBuilderState(() => ({ ...INITIAL_BUILDER_STATE }));
    setStoryText("");
  }

  function handleToggleStorySelect(id) {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]
    );
  }

  function handleDeleteSelectedStories() {
    if (selectedIds.length === 0) {
      alert("Please select at least one story to delete.");
      return;
    }

    setSavedStories((prev) =>
      prev.filter((story) => !selectedIds.includes(story.id))
    );
    setSelectedIds([]);
  }

  function handleLoadStory(id) {
    const story = savedStories.find((s) => s.id === id);
    if (!story) return;

    setStoryText(story.text);
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
          characters={characters}
          sidekicks={sidekicks}
          settings={settings}
          actions={actions}
          isGenerating={isGenerating}
        />
      </div>

      <Library
        stories={savedStories}
        selectedIds={selectedIds}
        onToggleSelect={handleToggleStorySelect}
        onDeleteSelected={handleDeleteSelectedStories}
        onLoadStory={handleLoadStory}
      />
    </section>
  );
}

export default Home;

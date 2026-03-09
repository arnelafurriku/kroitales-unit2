import IconButton from "./IconButton.jsx";

function Builder({
  builderState,
  storyText,
  onFieldChange,
  onGenerate,
  onSaveStory,
  onReadAloud,
  onStopReadAloud,
  isGenerating,
}) {
  const { character, sidekick, setting, action, notes, title } = builderState;

  return (
    <>
      <div className="panel panel-left">
        <div className="form-row">
          <label>Character</label>
          <input
            type="text"
            placeholder="Enter a character name"
            value={character}
            onChange={(e) => onFieldChange("character", e.target.value)}
          />
        </div>

        <div className="form-row">
          <label>Sidekick</label>
          <input
            type="text"
            placeholder="Enter a sidekick name"
            value={sidekick}
            onChange={(e) => onFieldChange("sidekick", e.target.value)}
          />
        </div>

        <div className="form-row">
          <label>Setting</label>
          <input
            type="text"
            placeholder="Enter a setting"
            value={setting}
            onChange={(e) => onFieldChange("setting", e.target.value)}
          />
        </div>

        <div className="form-row">
          <label>Action</label>
          <input
            type="text"
            placeholder="Enter an action"
            value={action}
            onChange={(e) => onFieldChange("action", e.target.value)}
          />
        </div>

        <div className="form-row">
          <label>Notes / Tags</label>
          <input
            type="text"
            placeholder="e.g. bedtime, moon, forest"
            value={notes}
            onChange={(e) => onFieldChange("notes", e.target.value)}
          />
        </div>

        <div className="form-row">
          <label>Story Title</label>
          <input
            type="text"
            placeholder="Give your story a title"
            value={title}
            onChange={(e) => onFieldChange("title", e.target.value)}
          />
        </div>

        <div className="btn-center">
          <button
            className={`btn ${isGenerating ? "btn--loading" : ""}`}
            type="button"
            onClick={onGenerate}
            disabled={isGenerating}
          >
            {isGenerating ? (
              <>
                <span className="spinner" aria-hidden="true" />
                Generating...
              </>
            ) : (
              "Generate Story"
            )}
          </button>
        </div>

        <div className="btn-center">
          <button className="btn" type="button" onClick={onSaveStory}>
            Save Story
          </button>
        </div>
      </div>

      <div className="panel panel-right">
        <h3 className="story-title">Your Story</h3>
        <textarea
          className="story-text"
          readOnly
          value={
            storyText || "Your story will appear here after you click Generate."
          }
        />

        <div className="story-actions">
          <IconButton type="button" icon="🔊" onClick={onReadAloud}>
            Read Aloud
          </IconButton>

          <button
            className="btn delete"
            type="button"
            onClick={onStopReadAloud}
          >
            Stop
          </button>
        </div>
      </div>
    </>
  );
}

export default Builder;
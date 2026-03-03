function Library({
  stories,
  selectedIds,
  onToggleSelect,
  onDeleteSelected,
  onLoadStory,
}) {
  const hasStories = stories && stories.length > 0;

  return (
    <div className="panel panel-saved" style={{ marginTop: 24 }}>
      <h3 className="story-title" style={{ textAlign: "center" }}>
        Saved Stories
      </h3>

      <div className="saved-actions">
        <button
          className="btn delete"
          type="button"
          onClick={onDeleteSelected}
        >
          Delete
        </button>
      </div>

      <ul className="list list--stories">
        {hasStories ? (
          stories.map((story) => (
            <li key={story.id} className="list-item">
              <div className="story-item">
                <input
                  type="checkbox"
                  checked={selectedIds.includes(story.id)}
                  onChange={() => onToggleSelect(story.id)}
                />
                <div className="story-item__content">
                  <h4>{story.title}</h4>
                  <p>{story.text}</p>
                  <button
                    type="button"
                    className="btn"
                    style={{ marginTop: "6px" }}
                    onClick={() => onLoadStory(story.id)}
                  >
                    Load in preview
                  </button>
                </div>
              </div>
            </li>
          ))
        ) : (
          <li className="list-item">
            <p style={{ margin: 0 }}>
              No stories saved yet. Generate a story and click Save to add it
              here.
            </p>
          </li>
        )}
      </ul>
    </div>
  );
}

export default Library;

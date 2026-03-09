function Library({ stories, onViewStory, onEditStory, onDeleteStory }) {
  const hasStories = stories && stories.length > 0;

  return (
    <div className="panel panel-saved" style={{ marginTop: 24 }}>
      <h3 className="story-title" style={{ textAlign: "center" }}>
        Saved Stories
      </h3>

      <div className="stories-table">
        <div className="stories-table__header">
          <div>Title</div>
          <div>Created</div>
          <div>Actions</div>
        </div>

        {hasStories ? (
          stories.map((story) => (
            <div key={story.id} className="stories-table__row">
              <div className="stories-table__title">
                {story.title || "Untitled story"}
              </div>

              <div className="stories-table__date">
                {story.createdAt || "—"}
              </div>

              <div className="stories-table__actions">
                <button
                  type="button"
                  className="btn"
                  onClick={() => onViewStory(story.id)}
                >
                  View
                </button>

                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => onEditStory(story.id)}
                >
                  Edit
                </button>

                <button
                  type="button"
                  className="btn delete"
                  onClick={() => onDeleteStory(story.id)}
                >
                  Delete
                </button>
              </div>
            </div>
          ))
        ) : (
          <div className="stories-table__empty">
            No stories saved yet. Generate a story and click Save to add it
            here.
          </div>
        )}
      </div>
    </div>
  );
}

export default Library;
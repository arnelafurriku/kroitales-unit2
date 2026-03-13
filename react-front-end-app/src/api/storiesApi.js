const BASE_URL = import.meta.env.VITE_API_URL;

async function handleResponse(response, defaultMessage) {
  if (response.ok) {
    if (response.status === 204) return null;
    return response.json();
  }

  let errorMessage = `${defaultMessage}: ${response.status}`;

  try {
    const errorData = await response.json();
    errorMessage =
      errorData.message ||
      errorData.error ||
      JSON.stringify(errorData) ||
      errorMessage;
  } catch {
    try {
      const errorText = await response.text();
      if (errorText) {
        errorMessage = errorText;
      }
    } catch {
      // keep default message
    }
  }

  throw new Error(errorMessage);
}

export async function generateStory(story) {
  const response = await fetch(`${BASE_URL}/api/stories/generate`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(story),
  });

  return handleResponse(response, "Failed to generate story");
}

// GET all stories
export async function getAllStories() {
  const response = await fetch(`${BASE_URL}/api/stories`);
  return handleResponse(response, "Failed to fetch stories");
}

// CREATE story
export async function createStory(story) {
  const response = await fetch(`${BASE_URL}/api/stories`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(story),
  });

  return handleResponse(response, "Failed to create story");
}

// UPDATE story
export async function updateStory(id, story) {
  const response = await fetch(`${BASE_URL}/api/stories/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(story),
  });

  return handleResponse(response, "Failed to update story");
}

// DELETE story
export async function deleteStory(id) {
  const response = await fetch(`${BASE_URL}/api/stories/${id}`, {
    method: "DELETE",
  });

  return handleResponse(response, "Failed to delete story");
}
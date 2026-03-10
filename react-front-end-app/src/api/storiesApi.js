const BASE_URL = import.meta.env.VITE_API_URL;

export async function getAllStories() {
  const response = await fetch(`${BASE_URL}/api/stories`);

  if (!response.ok) {
    throw new Error(`Failed to fetch stories: ${response.status}`);
  }

  return response.json();
}

export async function createStory(story) {
  const response = await fetch(`${BASE_URL}/api/stories`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(story),
  });

  if (!response.ok) {
    throw new Error(`Failed to create story: ${response.status}`);
  }

  return response.json();
}

export async function updateStory(id, story) {
  const response = await fetch(`${BASE_URL}/api/stories/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(story),
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Failed to update story: ${response.status} ${errorText}`);
  }

  return response.json();
}

export async function deleteStory(id) {
  const response = await fetch(`${BASE_URL}/api/stories/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error(`Failed to delete story: ${response.status}`);
  }

  return true;
}

const BASE_URL = import.meta.env.VITE_API_URL;

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
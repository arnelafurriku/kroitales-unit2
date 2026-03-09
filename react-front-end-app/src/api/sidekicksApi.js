const BASE_URL = import.meta.env.VITE_API_URL;

export async function createSidekick(sidekick) {
  const response = await fetch(`${BASE_URL}/api/sidekicks`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(sidekick),
  });

  if (!response.ok) {
    throw new Error(`Failed to create sidekick: ${response.status}`);
  }

  return response.json();
}
const BASE_URL = import.meta.env.VITE_API_URL;

// CREATE
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

// UPDATE
export async function updateSidekick(id, sidekick) {
  const response = await fetch(`${BASE_URL}/api/sidekicks/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(sidekick),
  });

  if (!response.ok) {
    throw new Error(`Failed to update sidekick: ${response.status}`);
  }

  return response.json();
}
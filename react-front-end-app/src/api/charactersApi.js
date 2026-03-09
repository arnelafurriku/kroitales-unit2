const BASE_URL = import.meta.env.VITE_API_URL;

export async function createCharacter(character) {
  const response = await fetch(`${BASE_URL}/api/characters`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(character),
  });

  if (!response.ok) {
    throw new Error(`Failed to create character: ${response.status}`);
  }

  return response.json();
}
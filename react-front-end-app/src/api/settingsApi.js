const BASE_URL = import.meta.env.VITE_API_URL;

export async function createSetting(setting) {
  const response = await fetch(`${BASE_URL}/api/settings`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(setting),
  });

  if (!response.ok) {
    throw new Error(`Failed to create setting: ${response.status}`);
  }

  return response.json();
}
const BASE_URL = import.meta.env.VITE_API_URL;

// CREATE
export async function createAction(action) {
  const response = await fetch(`${BASE_URL}/api/actions`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(action),
  });

  if (!response.ok) {
    throw new Error(`Failed to create action: ${response.status}`);
  }

  return response.json();
}

// UPDATE
export async function updateAction(id, action) {
  const response = await fetch(`${BASE_URL}/api/actions/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(action),
  });

  if (!response.ok) {
    throw new Error(`Failed to update action: ${response.status}`);
  }

  return response.json();
}
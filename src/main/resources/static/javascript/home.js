// Function to retrieve the logged-in user's ID from the cookie
function getUserId() {
  const cookieValue = document.cookie
    .split('; ')
    .find((row) => row.startsWith('userId'))
    .split('=')[1];

  return parseInt(cookieValue);
}

// Function to clear the user ID cookie on logout
function logout() {
  document.cookie = 'userId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
  window.location.href = '/login.html';
}

// Function to handle the form submission for adding a new note
function addNote() {
  const titleInput = document.getElementById('title');
  const contentInput = document.getElementById('content');

  const newNote = {
    userId: getUserId(),
    title: titleInput.value,
    content: contentInput.value,
  };

  fetch('/api/v1/notes', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newNote),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        titleInput.value = '';
        contentInput.value = '';
        getNotes();
      }
    })
    .catch((error) => console.log(error));
}

// Function to retrieve all notes associated with the logged-in user
function getNotes() {
  const userId = getUserId();

  fetch(`/api/v1/notes/user/${userId}`)
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        createNoteCards(data.notes);
      }
    })
    .catch((error) => console.log(error));
}

// Function to create note cards based on the provided array of notes
function createNoteCards(notes) {
  const container = document.getElementById('notes-container');
  container.innerHTML = '';

  notes.forEach((note) => {
    const card = document.createElement('div');
    card.className = 'card';
    card.innerHTML = `
      <div class="card-body">
        <h5 class="card-title">${note.title}</h5>
        <p class="card-text">${note.content}</p>
        <button class="btn btn-primary" onclick="editNote(${note.id})">Edit</button>
        <button class="btn btn-danger" onclick="deleteNote(${note.id})">Delete</button>
      </div>
    `;

    container.appendChild(card);
  });
}

// Function to populate the modal with the selected note's details
function editNote(noteId) {
  fetch(`/api/v1/notes/${noteId}`)
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        const note = data.note;
        const titleInput = document.getElementById('edit-title');
        const contentInput = document.getElementById('edit-content');
        const saveButton = document.getElementById('save-button');

        titleInput.value = note.title;
        contentInput.value = note.content;
        saveButton.setAttribute('data-note-id', note.id);

        $('#editModal').modal('show');
      }
    })
    .catch((error) => console.log(error));
}

// Function to update the selected note
function updateNote() {
  const noteId = parseInt(document.getElementById('save-button').getAttribute('data-note-id'));
  const titleInput = document.getElementById('edit-title');
  const contentInput = document.getElementById('edit-content');

  const updatedNote = {
    title: titleInput.value,
    content: contentInput.value,
  };

  fetch(`/api/v1/notes/${noteId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updatedNote),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        $('#editModal').modal('hide');
        getNotes();
      }
    })
    .catch((error) => console.log(error));
}

// Function to delete the selected note
function deleteNote(noteId) {
  fetch(`/api/v1/notes/${noteId}`, {
    method: 'DELETE',
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        getNotes();
      }
    })
    .catch((error) => console.log(error));
}

// Retrieve notes when the page loads
getNotes();

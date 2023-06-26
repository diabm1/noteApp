// Function to handle the form submission for user login
function handleSubmit(event) {
  event.preventDefault();

  const usernameInput = document.getElementById('username');
  const passwordInput = document.getElementById('password');

  const userCredentials = {
    username: usernameInput.value,
    password: passwordInput.value,
  };

  fetch('/api/v1/users/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userCredentials),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        document.cookie = `userId=${data.userId}`;
        window.location.href = '/home.html';
      }
    })
    .catch((error) => console.log(error));
}

// Add event listener to the login form
const loginForm = document.getElementById('login-form');
loginForm.addEventListener('submit', handleSubmit);

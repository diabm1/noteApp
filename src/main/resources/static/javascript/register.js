// Function to handle the form submission for user registration
function handleSubmit(event) {
  event.preventDefault();

  const usernameInput = document.getElementById('username');
  const passwordInput = document.getElementById('password');

  const newUser = {
    username: usernameInput.value,
    password: passwordInput.value,
  };

  fetch('/api/v1/users/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newUser),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        window.location.href = '/login.html';
      }
    })
    .catch((error) => console.log(error));
}

// Add event listener to the registration form
const registerForm = document.getElementById('register-form');
registerForm.addEventListener('submit', handleSubmit);

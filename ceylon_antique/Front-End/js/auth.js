// DOM Elements
const container = document.querySelector('.container');
const registerBtn = document.querySelector('.register-btn');
const loginBtn = document.querySelector('.login-btn');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const alertContainer = document.getElementById('alertContainer');

// API Base URL - Update this to match your backend URL
const API_BASE_URL = 'http://localhost:8080';

// Switch between Login <-> Register
registerBtn.addEventListener('click', () => {
    container.classList.add('active');
});

loginBtn.addEventListener('click', () => {
    container.classList.remove('active');
});

// Toggle password visibility
const toggleIcons = document.querySelectorAll('.togglePassword');

toggleIcons.forEach(icon => {
    icon.addEventListener('click', () => {
        const input = document.getElementById(icon.dataset.target);

        if (input.type === "password") {
            input.type = "text";
            icon.classList.remove('bx-hide');
            icon.classList.add('bx-show');
        } else {
            input.type = "password";
            icon.classList.remove('bx-show');
            icon.classList.add('bx-hide');
        }
    });
});

// Cookie setter
function setCookie(name, value, daysToExpire) {
    const date = new Date();
    date.setTime(date.getTime() + (daysToExpire * 24 * 60 * 60 * 1000));
    const expires = "expires=" + date.toUTCString();
    document.cookie = `${name}=${value};${expires};path=/`;
}

// Alert System
function showAlert(message, type = 'info') {
    const alert = document.createElement('div');
    alert.className = `alert ${type}`;
    alert.innerHTML = `
        <span>${message}</span>
        <button class="close-btn">&times;</button>
    `;

    alertContainer.appendChild(alert);

    // Add close functionality
    const closeBtn = alert.querySelector('.close-btn');
    closeBtn.addEventListener('click', () => {
        alert.style.animation = 'slideOut 0.3s forwards';
        setTimeout(() => {
            alert.remove();
        }, 300);
    });

    // Auto remove after 5 seconds
    setTimeout(() => {
        if (alert.parentNode) {
            alert.style.animation = 'slideOut 0.3s forwards';
            setTimeout(() => {
                if (alert.parentNode) alert.remove();
            }, 300);
        }
    }, 5000);
}

// Handle Login Form Submission
loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('loginUsername').value.trim();
    const password = document.getElementById('loginPassword').value.trim();

    if (!username || !password) {
        showAlert('Please fill all fields', 'error');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (response.ok) {
            showAlert('Login successful!', 'success');

            // Store the JWT token in cookie (7 days)
            const token = data.data.accessToken;
            setCookie("token", token, 7);

            // Decode JWT to check role
            const payload = JSON.parse(atob(token.split('.')[1]));
            const role = payload.role || payload.authorities || "USER";

            // Redirect based on role
            setTimeout(() => {
                if (role.includes("ADMIN")) {
                    window.location.href = "AdminDashboard.html";
                } else {
                    window.location.href = "UserDashboard.html";
                }
            }, 1500);
        } else {
            showAlert(data.data || 'Login failed', 'error');
        }
    } catch (error) {
        showAlert('Network error. Please try again.', 'error');
        console.error('Login error:', error);
    }
});

// Handle Register Form Submission
registerForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('registerUsername').value.trim();
    const email = document.getElementById('registerEmail').value.trim();
    const password = document.getElementById('registerPassword').value.trim();

    if (!username || !email || !password) {
        showAlert('Please fill all fields', 'error');
        return;
    }

    if (password.length < 5) {
        showAlert('Password must be at least 5 characters', 'error');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, email, password })
        });

        const data = await response.json();

        if (response.ok) {
            showAlert('Registration successful! Please login.', 'success');

            // Switch to login form after successful registration
            setTimeout(() => {
                container.classList.remove('active');
                registerForm.reset();
            }, 1500);
        } else {
            showAlert(data.data || 'Registration failed', 'error');
        }
    } catch (error) {
        showAlert('Network error. Please try again.', 'error');
        console.error('Registration error:', error);
    }
});

// Check if user is already logged in
function checkAuthStatus() {
    const token = getCookie('token');
    if (token) {
        // Verify token is still valid
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            const currentTime = Date.now() / 1000;

            if (payload.exp > currentTime) {
                // Token is still valid, redirect to appropriate dashboard
                const role = payload.role || payload.authorities || "USER";
                if (role.includes("ADMIN")) {
                    window.location.href = "AdminDashboard.html";
                } else {
                    window.location.href = "UserDashboard.html";
                }
            }
        } catch (error) {
            // Token is invalid, remove it
            document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        }
    }
}

// Get cookie value by name
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

// Check authentication status on page load
checkAuthStatus();
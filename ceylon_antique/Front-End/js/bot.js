// =============================
// API configuration
// =============================
const API_BASE_URL = 'http://localhost:8080';

// =============================
// Cookie Helpers
// =============================
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

// =============================
// DOM elements
// =============================
const aiAssistant = document.querySelector('.ai-assistant');
const chatContainer = document.getElementById('chatContainer');
const chatClose = document.getElementById('chatClose');
const chatMessages = document.getElementById('chatMessages');
const chatInput = document.getElementById('chatInput');
const chatSend = document.getElementById('chatSend');

// =============================
// Toggle chat window
// =============================
aiAssistant.addEventListener('click', function () {
    chatContainer.classList.toggle('open');
    aiAssistant.classList.toggle('open');
    if (chatContainer.classList.contains('open')) {
        chatInput.focus();
    }
});

// =============================
// Close chat window
// =============================
chatClose.addEventListener('click', function () {
    chatContainer.classList.remove('open');
    aiAssistant.classList.remove('open');
});

// =============================
// Send message function
// =============================
async function sendMessage() {
    const message = chatInput.value.trim();
    if (!message) return;

    // Add user message to chat
    addMessage(message, 'user');
    chatInput.value = '';

    // Get JWT token from cookie
    const yourToken = getCookie("token");
    if (!yourToken) {
        addMessage("⚠️ You are not logged in. Please login first.", "bot");
        return;
    }

    try {
        // Send message to backend with Authorization token
        const response = await fetch(`${API_BASE_URL}/bot/chat?prompt=${encodeURIComponent(message)}`, {
            method: "GET", // ❗ change to POST if backend expects POST
            headers: {
                "Authorization": `Bearer ${yourToken}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error(`Failed to get response from AI (Status ${response.status})`);
        }

        const botResponse = await response.text();
        addMessage(botResponse, 'bot');
    } catch (error) {
        console.error('Chat error:', error);
        addMessage("❌ Sorry, I'm having trouble connecting right now. Please try again later.", 'bot');
    }
}

// =============================
// Add message to chat
// =============================
function addMessage(text, sender) {
    const messageElement = document.createElement('div');
    messageElement.classList.add('message', sender);
    messageElement.textContent = text;
    chatMessages.appendChild(messageElement);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

// =============================
// Event listeners
// =============================
chatSend.addEventListener('click', sendMessage);

chatInput.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
});

// =============================
// On page load
// =============================
document.addEventListener('DOMContentLoaded', function () {
    console.log('Ceylon Antiques Premium UI Loaded');
});

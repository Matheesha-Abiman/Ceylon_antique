// Navigation function
function navigateTo(page) {
    if (page === '') {
        window.location.href = '../index.html';
    } else {
        window.location.href = `admin${page.charAt(0).toUpperCase() + page.slice(1)}.html`;
    }
}

// DOM Elements
const contactSupportBtn = document.getElementById('contactSupportBtn');
const supportModal = document.getElementById('supportModal');
const closeBtn = document.querySelector('.close-btn');
const cancelSupportBtn = document.getElementById('cancelSupportBtn');
const submitSupportBtn = document.getElementById('submitSupportBtn');
const faqItems = document.querySelectorAll('.faq-item');
const helpSearch = document.getElementById('helpSearch');
const liveChatBtn = document.getElementById('liveChatBtn');

// Event Listeners
contactSupportBtn.addEventListener('click', openSupportModal);
closeBtn.addEventListener('click', closeModal);
cancelSupportBtn.addEventListener('click', closeModal);
submitSupportBtn.addEventListener('click', submitSupportRequest);
liveChatBtn.addEventListener('click', startLiveChat);
helpSearch.addEventListener('input', searchHelpArticles);

// FAQ toggle functionality
faqItems.forEach(item => {
    const question = item.querySelector('.faq-question');
    question.addEventListener('click', () => {
        item.classList.toggle('active');
    });
});

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target === supportModal) {
        closeModal();
    }
});

// DOM Content Loaded
document.addEventListener('DOMContentLoaded', function() {
    // Menu item interaction
    const menuItems = document.querySelectorAll('.menu-item');

    menuItems.forEach(item => {
        item.addEventListener('click', function() {
            const isActive = this.classList.contains('active');
            if (!isActive) {
                menuItems.forEach(i => i.classList.remove('active'));
                this.classList.add('active');
            }
        });
    });

    // Initialize help page functionality
    initHelpPage();
});

// Functions
function openSupportModal() {
    supportModal.style.display = 'flex';
}

function closeModal() {
    supportModal.style.display = 'none';
}

function submitSupportRequest() {
    const subject = document.getElementById('supportSubject').value;
    const description = document.getElementById('supportDescription').value;
    const priority = document.getElementById('supportPriority').value;

    if (!subject || !description) {
        showNotification('Please fill in all required fields', 'error');
        return;
    }

    // In a real application, this would send the support request to a server
    console.log('Support request submitted:', {
        subject,
        description,
        priority
    });

    showNotification('Support request submitted successfully! We will get back to you soon.', 'success');
    closeModal();

    // Clear form
    document.getElementById('supportForm').reset();
}

function startLiveChat() {
    // In a real application, this would initiate a live chat session
    showNotification('Connecting you to a support agent...', 'info');

    // Simulate chat connection
    setTimeout(() => {
        showNotification('You are now connected to a support agent!', 'success');
    }, 2000);
}

function searchHelpArticles() {
    const searchTerm = helpSearch.value.toLowerCase();

    if (searchTerm.length < 2) {
        // Show all content if search term is too short
        document.querySelectorAll('.help-card, .faq-item').forEach(item => {
            item.style.display = 'block';
        });
        return;
    }

    // Search through help cards
    document.querySelectorAll('.help-card').forEach(card => {
        const title = card.querySelector('h3').textContent.toLowerCase();
        const description = card.querySelector('p').textContent.toLowerCase();

        if (title.includes(searchTerm) || description.includes(searchTerm)) {
            card.style.display = 'block';
            // Highlight matching text
            highlightText(card, searchTerm);
        } else {
            card.style.display = 'none';
        }
    });

    // Search through FAQ items
    document.querySelectorAll('.faq-item').forEach(item => {
        const question = item.querySelector('h3').textContent.toLowerCase();
        const answer = item.querySelector('p').textContent.toLowerCase();

        if (question.includes(searchTerm) || answer.includes(searchTerm)) {
            item.style.display = 'block';
            // Expand FAQ items that match search
            item.classList.add('active');
            // Highlight matching text
            highlightText(item, searchTerm);
        } else {
            item.style.display = 'none';
        }
    });
}

function highlightText(element, searchTerm) {
    // Remove previous highlights
    element.querySelectorAll('.highlight').forEach(highlight => {
        highlight.outerHTML = highlight.innerHTML;
    });

    // Highlight matching text
    const regex = new RegExp(searchTerm, 'gi');
    element.innerHTML = element.innerHTML.replace(regex, match =>
        `<span class="highlight" style="background-color: #ffeb3b; padding: 2px 0; border-radius: 3px;">${match}</span>`
    );
}

function showNotification(message, type = 'info') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification-toast notification-${type}`;
    notification.innerHTML = `
        <div class="notification-content">
            <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'exclamation-circle' : 'info-circle'}"></i>
            <span>${message}</span>
        </div>
        <button class="notification-close">
            <i class="fas fa-times"></i>
        </button>
    `;

    // Add styles for notification
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background: white;
        padding: 15px 20px;
        border-radius: var(--border-radius);
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        display: flex;
        align-items: center;
        justify-content: space-between;
        z-index: 10000;
        min-width: 300px;
        border-left: 4px solid ${type === 'success' ? 'var(--success)' : type === 'error' ? 'var(--danger)' : 'var(--info)'};
        transform: translateX(100%);
        transition: transform 0.3s ease;
    `;

    // Add styles for notification content
    notification.querySelector('.notification-content').style.cssText = `
        display: flex;
        align-items: center;
        gap: 10px;
    `;

    // Add icon color
    notification.querySelector('i').style.color = type === 'success' ? 'var(--success)' : type === 'error' ? 'var(--danger)' : 'var(--info)';

    // Add close button styles
    notification.querySelector('.notification-close').style.cssText = `
        background: none;
        border: none;
        cursor: pointer;
        color: var(--gray);
        margin-left: 15px;
    `;

    // Add close functionality
    notification.querySelector('.notification-close').addEventListener('click', () => {
        notification.remove();
    });

    // Add to document
    document.body.appendChild(notification);

    // Animate in
    setTimeout(() => {
        notification.style.transform = 'translateX(0)';
    }, 10);

    // Auto remove after 5 seconds
    setTimeout(() => {
        if (notification.parentNode) {
            notification.style.transform = 'translateX(100%)';
            setTimeout(() => {
                if (notification.parentNode) {
                    notification.remove();
                }
            }, 300);
        }
    }, 5000);
}

// Help page initialization
function initHelpPage() {
    console.log('Help page initialized');

    // Initialize any help-specific functionality
    // For example, you could load most popular articles or recent updates

    // Sample: Auto-expand first FAQ item
    if (faqItems.length > 0) {
        faqItems[0].classList.add('active');
    }
}
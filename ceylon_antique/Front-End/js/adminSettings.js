// Navigation function
function navigateTo(page) {
    if (page === '') {
        window.location.href = '../index.html';
    } else {
        window.location.href = `admin${page.charAt(0).toUpperCase() + page.slice(1)}.html`;
    }
}

// DOM Elements
const saveSettingsBtn = document.getElementById('saveSettingsBtn');
const tabLinks = document.querySelectorAll('.tab-link');
const tabContents = document.querySelectorAll('.tab-content');
const backupNowBtn = document.getElementById('backupNowBtn');
const primaryColorInput = document.getElementById('primaryColor');
const colorValueSpan = primaryColorInput.nextElementSibling;

// Event Listeners
saveSettingsBtn.addEventListener('click', saveSettings);
backupNowBtn.addEventListener('click', backupNow);
primaryColorInput.addEventListener('input', updateColorValue);

// Tab navigation
tabLinks.forEach(tab => {
    tab.addEventListener('click', () => {
        const tabId = tab.getAttribute('data-tab');

        // Remove active class from all tabs and contents
        tabLinks.forEach(t => t.classList.remove('active'));
        tabContents.forEach(c => c.classList.remove('active'));

        // Add active class to clicked tab and corresponding content
        tab.classList.add('active');
        document.getElementById(tabId).classList.add('active');
    });
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

    // Initialize settings page functionality
    initSettingsPage();
});

// Functions
function saveSettings() {
    // In a real application, this would save all settings to the server
    const settings = {
        siteName: document.getElementById('siteName').value,
        siteDescription: document.getElementById('siteDescription').value,
        adminEmail: document.getElementById('adminEmail').value,
        timezone: document.getElementById('timezone').value,
        dateFormat: document.getElementById('dateFormat').value,
        theme: document.getElementById('theme').value,
        primaryColor: document.getElementById('primaryColor').value,
        sidebarCollapsed: document.getElementById('sidebarCollapsed').checked,
        fixedHeader: document.getElementById('fixedHeader').checked,
        emailNotifications: document.getElementById('emailNotifications').checked,
        notificationFrequency: document.getElementById('notificationFrequency').value,
        notifyNewUsers: document.getElementById('notifyNewUsers').checked,
        notifyPayments: document.getElementById('notifyPayments').checked,
        notifyReviews: document.getElementById('notifyReviews').checked,
        twoFactorAuth: document.getElementById('twoFactorAuth').checked,
        sessionTimeout: document.getElementById('sessionTimeout').value,
        minPasswordLength: document.getElementById('minPasswordLength').value,
        requireSpecialChars: document.getElementById('requireSpecialChars').checked,
        cacheLifetime: document.getElementById('cacheLifetime').value,
        debugMode: document.getElementById('debugMode').checked,
        maintenanceMode: document.getElementById('maintenanceMode').checked,
        backupFrequency: document.getElementById('backupFrequency').value
    };

    console.log('Saving settings:', settings);

    // Show success message
    showNotification('Settings saved successfully!', 'success');
}

function backupNow() {
    // In a real application, this would initiate a backup process
    showNotification('Backup process started...', 'info');

    // Simulate backup process
    setTimeout(() => {
        showNotification('Backup completed successfully!', 'success');
    }, 3000);
}

function updateColorValue() {
    colorValueSpan.textContent = primaryColorInput.value;
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

// Settings page initialization
function initSettingsPage() {
    console.log('Settings page initialized');

    // Load saved settings (in a real application, this would come from an API)
    const savedSettings = {
        siteName: 'My Admin Panel',
        siteDescription: 'A powerful admin panel for managing your website',
        adminEmail: 'admin@example.com',
        timezone: 'UTC-8',
        dateFormat: 'MM/DD/YYYY',
        theme: 'light',
        primaryColor: '#4361ee',
        sidebarCollapsed: true,
        fixedHeader: true,
        emailNotifications: true,
        notificationFrequency: 'hourly',
        notifyNewUsers: true,
        notifyPayments: true,
        notifyReviews: false,
        twoFactorAuth: false,
        sessionTimeout: '60',
        minPasswordLength: '8',
        requireSpecialChars: true,
        cacheLifetime: '1800',
        debugMode: false,
        maintenanceMode: false,
        backupFrequency: 'weekly'
    };

    // Apply saved settings to form
    document.getElementById('siteName').value = savedSettings.siteName;
    document.getElementById('siteDescription').value = savedSettings.siteDescription;
    document.getElementById('adminEmail').value = savedSettings.adminEmail;
    document.getElementById('timezone').value = savedSettings.timezone;
    document.getElementById('dateFormat').value = savedSettings.dateFormat;
    document.getElementById('theme').value = savedSettings.theme;
    document.getElementById('primaryColor').value = savedSettings.primaryColor;
    document.getElementById('sidebarCollapsed').checked = savedSettings.sidebarCollapsed;
    document.getElementById('fixedHeader').checked = savedSettings.fixedHeader;
    document.getElementById('emailNotifications').checked = savedSettings.emailNotifications;
    document.getElementById('notificationFrequency').value = savedSettings.notificationFrequency;
    document.getElementById('notifyNewUsers').checked = savedSettings.notifyNewUsers;
    document.getElementById('notifyPayments').checked = savedSettings.notifyPayments;
    document.getElementById('notifyReviews').checked = savedSettings.notifyReviews;
    document.getElementById('twoFactorAuth').checked = savedSettings.twoFactorAuth;
    document.getElementById('sessionTimeout').value = savedSettings.sessionTimeout;
    document.getElementById('minPasswordLength').value = savedSettings.minPasswordLength;
    document.getElementById('requireSpecialChars').checked = savedSettings.requireSpecialChars;
    document.getElementById('cacheLifetime').value = savedSettings.cacheLifetime;
    document.getElementById('debugMode').checked = savedSettings.debugMode;
    document.getElementById('maintenanceMode').checked = savedSettings.maintenanceMode;
    document.getElementById('backupFrequency').value = savedSettings.backupFrequency;

    // Update color value display
    updateColorValue();

    // Initialize any other settings-specific functionality
}
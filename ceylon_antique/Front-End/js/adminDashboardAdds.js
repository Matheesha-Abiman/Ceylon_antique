// Navigation function
function navigateTo(page) {
    if (page === '') {
        window.location.href = '../index.html';
    } else {
        window.location.href = `admin${page.charAt(0).toUpperCase() + page.slice(1)}.html`;
    }
}

// DOM Elements
const createAdBtn = document.getElementById('createAdBtn');
const adModal = document.getElementById('adModal');
const modalTitle = document.getElementById('modalTitle');
const closeBtn = document.querySelector('.close-btn');
const cancelBtn = document.getElementById('cancelBtn');
const saveAdBtn = document.getElementById('saveAdBtn');
const editButtons = document.querySelectorAll('.edit-btn');
const deleteButtons = document.querySelectorAll('.delete-btn');

// Event Listeners
createAdBtn.addEventListener('click', openCreateAdModal);
closeBtn.addEventListener('click', closeModal);
cancelBtn.addEventListener('click', closeModal);

// Edit buttons
editButtons.forEach(button => {
    button.addEventListener('click', () => {
        openEditAdModal();
    });
});

// Delete buttons
deleteButtons.forEach(button => {
    button.addEventListener('click', () => {
        deleteAdCampaign();
    });
});

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target === adModal) {
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

    // Initialize ads page functionality
    initAdsPage();
});

// Functions
function openCreateAdModal() {
    modalTitle.textContent = 'Create New Ad Campaign';
    adModal.style.display = 'flex';
    // Reset form
    document.getElementById('adForm').reset();
}

function openEditAdModal() {
    modalTitle.textContent = 'Edit Ad Campaign';
    adModal.style.display = 'flex';

    // In a real application, you would fetch the ad data
    // and populate the form fields. For this example, we'll use dummy data.
    document.getElementById('campaignName').value = 'Summer Sale Banner';
    document.getElementById('adType').value = 'banner';
    document.getElementById('startDate').value = '2023-06-01';
    document.getElementById('endDate').value = '2023-08-31';
    document.getElementById('targetUrl').value = 'https://example.com/summer-sale';
    document.getElementById('adStatus').value = 'active';
    document.getElementById('adBudget').value = '50';
}

function closeModal() {
    adModal.style.display = 'none';
}

function deleteAdCampaign() {
    if (confirm('Are you sure you want to delete this ad campaign?')) {
        // In a real application, you would send a delete request to the server
        alert('Ad campaign has been deleted.');
    }
}

// Ads page initialization
function initAdsPage() {
    console.log('Ads page initialized');
    // Add ads-specific initialization code here
}
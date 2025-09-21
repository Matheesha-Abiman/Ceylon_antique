// Navigation function
function navigateTo(page) {
    if (page === '') {
        window.location.href = '../index.html';
    } else {
        window.location.href = `admin${page.charAt(0).toUpperCase() + page.slice(1)}.html`;
    }
}

// DOM Elements
const exportBtn = document.getElementById('exportBtn');
const reviewModal = document.getElementById('reviewModal');
const closeBtn = document.querySelector('.close-btn');
const approveBtn = document.getElementById('approveBtn');
const rejectBtn = document.getElementById('rejectBtn');
const modalCloseBtn = document.getElementById('closeBtn');
const actionButtons = document.querySelectorAll('.action-btn');
const bulkAction = document.getElementById('bulkAction');
const applyBulkAction = document.querySelector('.table-actions .btn-primary');

// Event Listeners
exportBtn.addEventListener('click', exportReviews);
closeBtn.addEventListener('click', closeModal);
modalCloseBtn.addEventListener('click', closeModal);
approveBtn.addEventListener('click', approveReview);
rejectBtn.addEventListener('click', rejectReview);
applyBulkAction.addEventListener('click', handleBulkAction);

// Action buttons
actionButtons.forEach(button => {
    button.addEventListener('click', function() {
        const action = this.classList[1]; // approve-btn, reject-btn, or delete-btn
        const reviewId = this.closest('tr').querySelector('td:first-child').textContent;

        if (action === 'approve-btn') {
            approveReview(reviewId);
        } else if (action === 'reject-btn') {
            rejectReview(reviewId);
        } else if (action === 'delete-btn') {
            deleteReview(reviewId);
        }
    });
});

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target === reviewModal) {
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

    // Initialize reviews page functionality
    initReviewsPage();
});

// Functions
function exportReviews() {
    // In a real application, this would generate and download a CSV or PDF file
    alert('Exporting reviews data...');
    // Simulate export process
    setTimeout(() => {
        alert('Reviews exported successfully!');
    }, 1500);
}

function openReviewModal(reviewId) {
    // In a real application, you would fetch review details by ID
    // For this example, we'll use dummy data
    document.getElementById('modalTitle').textContent = `Review ${reviewId} Details`;
    reviewModal.style.display = 'flex';
}

function closeModal() {
    reviewModal.style.display = 'none';
}

function approveReview(reviewId = null) {
    if (reviewId) {
        // Single review approval
        const statusCell = document.querySelector(`td:contains("${reviewId}")`).closest('tr').querySelector('.status');
        statusCell.textContent = 'Approved';
        statusCell.className = 'status approved';
        alert(`Review ${reviewId} has been approved.`);
    } else {
        // Modal approval
        alert('Review has been approved.');
        closeModal();
    }
}

function rejectReview(reviewId = null) {
    if (reviewId) {
        // Single review rejection
        const statusCell = document.querySelector(`td:contains("${reviewId}")`).closest('tr').querySelector('.status');
        statusCell.textContent = 'Rejected';
        statusCell.className = 'status rejected';
        alert(`Review ${reviewId} has been rejected.`);
    } else {
        // Modal rejection
        alert('Review has been rejected.');
        closeModal();
    }
}

function deleteReview(reviewId) {
    if (confirm(`Are you sure you want to delete ${reviewId}?`)) {
        // In a real application, you would send a delete request to the server
        const row = document.querySelector(`td:contains("${reviewId}")`).closest('tr');
        row.remove();
        alert(`${reviewId} has been deleted.`);
    }
}

function handleBulkAction() {
    const action = bulkAction.value;
    if (!action) {
        alert('Please select a bulk action');
        return;
    }

    const selectedRows = document.querySelectorAll('.row-checkbox:checked');
    if (selectedRows.length === 0) {
        alert('Please select at least one review');
        return;
    }

    if (action === 'delete') {
        if (confirm(`Are you sure you want to delete ${selectedRows.length} review(s)?`)) {
            selectedRows.forEach(checkbox => {
                const row = checkbox.closest('tr');
                row.remove();
            });
            alert(`${selectedRows.length} review(s) deleted successfully.`);
        }
    } else {
        selectedRows.forEach(checkbox => {
            const row = checkbox.closest('tr');
            const statusCell = row.querySelector('.status');

            if (action === 'approve') {
                statusCell.textContent = 'Approved';
                statusCell.className = 'status approved';
            } else if (action === 'reject') {
                statusCell.textContent = 'Rejected';
                statusCell.className = 'status rejected';
            }
        });
        alert(`${selectedRows.length} review(s) updated successfully.`);
    }

    // Reset bulk action select
    bulkAction.value = '';
}

// Reviews page initialization
function initReviewsPage() {
    console.log('Reviews page initialized');

    // Set up row click event to open modal
    const rows = document.querySelectorAll('.data-table tbody tr');
    rows.forEach(row => {
        row.addEventListener('click', (e) => {
            // Don't open modal if clicking on action buttons
            if (!e.target.closest('.action-buttons')) {
                const reviewId = row.querySelector('td:first-child').textContent;
                openReviewModal(reviewId);
            }
        });
    });

    // Initialize any other reviews-specific functionality
}

// Helper function to select elements by containing text
Element.prototype.contains = function(text) {
    return this.textContent.includes(text);
};
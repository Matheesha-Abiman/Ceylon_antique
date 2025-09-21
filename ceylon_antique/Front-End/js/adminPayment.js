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
const addPaymentBtn = document.getElementById('addPaymentBtn');
const paymentModal = document.getElementById('paymentModal');
const closeBtn = document.querySelector('.close-btn');
const modalCloseBtn = document.getElementById('closeBtn');
const viewButtons = document.querySelectorAll('.view-btn');
const refundButtons = document.querySelectorAll('.refund-btn');
const approveButtons = document.querySelectorAll('.approve-btn');
const retryButtons = document.querySelectorAll('.retry-btn');
const bulkAction = document.getElementById('bulkAction');
const applyBulkAction = document.querySelector('.table-actions .btn-primary');

// Event Listeners
exportBtn.addEventListener('click', exportPayments);
addPaymentBtn.addEventListener('click', openAddPaymentModal);
closeBtn.addEventListener('click', closeModal);
modalCloseBtn.addEventListener('click', closeModal);
applyBulkAction.addEventListener('click', handleBulkAction);

// Action buttons
viewButtons.forEach(button => {
    button.addEventListener('click', function() {
        const paymentId = this.closest('tr').querySelector('td:first-child').textContent;
        openPaymentModal(paymentId);
    });
});

refundButtons.forEach(button => {
    button.addEventListener('click', function() {
        const paymentId = this.closest('tr').querySelector('td:first-child').textContent;
        processRefund(paymentId);
    });
});

approveButtons.forEach(button => {
    button.addEventListener('click', function() {
        const paymentId = this.closest('tr').querySelector('td:first-child').textContent;
        approvePayment(paymentId);
    });
});

retryButtons.forEach(button => {
    button.addEventListener('click', function() {
        const paymentId = this.closest('tr').querySelector('td:first-child').textContent;
        retryPayment(paymentId);
    });
});

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target === paymentModal) {
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

    // Initialize payments page functionality
    initPaymentsPage();
});

// Functions
function exportPayments() {
    // In a real application, this would generate and download a CSV or PDF file
    alert('Exporting payments data...');
    // Simulate export process
    setTimeout(() => {
        alert('Payments exported successfully!');
    }, 1500);
}

function openAddPaymentModal() {
    alert('Add payment functionality would open here');
    // In a real application, this would open a modal for adding a new payment
}

function openPaymentModal(paymentId) {
    // In a real application, you would fetch payment details by ID
    // For this example, we'll use dummy data
    document.getElementById('modalTitle').textContent = `Payment ${paymentId} Details`;

    // Update modal content based on payment ID
    const paymentInfo = {
        '#PAY-001': {
            amount: '$249.99',
            status: 'completed',
            customer: 'John Smith (john.smith@example.com)',
            method: 'Credit Card (Visa ending in 4567)',
            date: 'Jun 12, 2023 at 14:30',
            transactionId: 'TXN-789456123',
            description: 'Web Development Service Package'
        },
        '#PAY-002': {
            amount: '$149.50',
            status: 'completed',
            customer: 'Emma Johnson (emma.johnson@example.com)',
            method: 'PayPal',
            date: 'Jul 5, 2023 at 10:15',
            transactionId: 'TXN-789456124',
            description: 'Mobile App Development'
        },
        '#PAY-003': {
            amount: '$99.00',
            status: 'pending',
            customer: 'Michael Brown (michael.brown@example.com)',
            method: 'Bank Transfer',
            date: 'Aug 19, 2023 at 16:45',
            transactionId: 'TXN-789456125',
            description: 'UI/UX Design Package'
        },
        '#PAY-004': {
            amount: '$199.99',
            status: 'failed',
            customer: 'Sarah Williams (sarah.williams@example.com)',
            method: 'Credit Card (Mastercard ending in 1234)',
            date: 'Sep 2, 2023 at 09:30',
            transactionId: 'TXN-789456126',
            description: 'SEO Optimization Service'
        },
        '#PAY-005': {
            amount: '$299.00',
            status: 'refunded',
            customer: 'Robert Davis (robert.davis@example.com)',
            method: 'Cryptocurrency (Bitcoin)',
            date: 'Sep 15, 2023 at 11:20',
            transactionId: 'TXN-789456127',
            description: 'E-commerce Development'
        }
    };

    const payment = paymentInfo[paymentId];
    if (payment) {
        document.querySelector('.amount').textContent = payment.amount;
        document.querySelector('.payment-status .status').textContent = payment.status.charAt(0).toUpperCase() + payment.status.slice(1);
        document.querySelector('.payment-status .status').className = `status ${payment.status}`;

        const detailValues = document.querySelectorAll('.detail-value');
        detailValues[0].textContent = payment.customer;
        detailValues[1].textContent = payment.method;
        detailValues[2].textContent = payment.date;
        detailValues[3].textContent = payment.transactionId;
        detailValues[4].textContent = payment.description;
    }

    paymentModal.style.display = 'flex';
}

function closeModal() {
    paymentModal.style.display = 'none';
}

function processRefund(paymentId) {
    if (confirm(`Are you sure you want to process a refund for ${paymentId}?`)) {
        // In a real application, you would process the refund via payment gateway API
        const statusCell = document.querySelector(`td:contains("${paymentId}")`).closest('tr').querySelector('.status');
        statusCell.textContent = 'Refunded';
        statusCell.className = 'status refunded';

        // Remove refund button after refund is processed
        const refundBtn = document.querySelector(`td:contains("${paymentId}")`).closest('tr').querySelector('.refund-btn');
        if (refundBtn) {
            refundBtn.remove();
        }

        alert(`Refund processed successfully for ${paymentId}`);
    }
}

function approvePayment(paymentId) {
    if (confirm(`Are you sure you want to approve ${paymentId}?`)) {
        // In a real application, you would update payment status
        const statusCell = document.querySelector(`td:contains("${paymentId}")`).closest('tr').querySelector('.status');
        statusCell.textContent = 'Completed';
        statusCell.className = 'status completed';

        // Change approve button to refund button
        const actionCell = document.querySelector(`td:contains("${paymentId}")`).closest('tr').querySelector('.action-buttons');
        const approveBtn = actionCell.querySelector('.approve-btn');
        if (approveBtn) {
            approveBtn.className = 'action-btn refund-btn';
            approveBtn.innerHTML = '<i class="fas fa-undo"></i>';
            approveBtn.addEventListener('click', function() {
                processRefund(paymentId);
            });
        }

        alert(`Payment ${paymentId} approved successfully`);
    }
}

function retryPayment(paymentId) {
    if (confirm(`Are you sure you want to retry ${paymentId}?`)) {
        // In a real application, you would retry the payment processing
        const statusCell = document.querySelector(`td:contains("${paymentId}")`).closest('tr').querySelector('.status');
        statusCell.textContent = 'Pending';
        statusCell.className = 'status pending';

        // Change retry button to approve button
        const actionCell = document.querySelector(`td:contains("${paymentId}")`).closest('tr').querySelector('.action-buttons');
        const retryBtn = actionCell.querySelector('.retry-btn');
        if (retryBtn) {
            retryBtn.className = 'action-btn approve-btn';
            retryBtn.innerHTML = '<i class="fas fa-check"></i>';
            retryBtn.addEventListener('click', function() {
                approvePayment(paymentId);
            });
        }

        alert(`Payment ${paymentId} is being retried`);
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
        alert('Please select at least one payment');
        return;
    }

    if (action === 'export') {
        alert(`Exporting ${selectedRows.length} payment(s)...`);
        // In a real application, this would export the selected payments
        setTimeout(() => {
            alert(`${selectedRows.length} payment(s) exported successfully.`);
        }, 1500);
    } else if (action === 'refund') {
        if (confirm(`Are you sure you want to refund ${selectedRows.length} payment(s)?`)) {
            selectedRows.forEach(checkbox => {
                const row = checkbox.closest('tr');
                const paymentId = row.querySelector('td:first-child').textContent;
                const statusCell = row.querySelector('.status');

                if (statusCell.textContent === 'Completed') {
                    statusCell.textContent = 'Refunded';
                    statusCell.className = 'status refunded';

                    // Remove refund button
                    const refundBtn = row.querySelector('.refund-btn');
                    if (refundBtn) {
                        refundBtn.remove();
                    }
                }
            });
            alert(`${selectedRows.length} payment(s) refunded successfully.`);
        }
    }

    // Reset bulk action select
    bulkAction.value = '';
}

// Payments page initialization
function initPaymentsPage() {
    console.log('Payments page initialized');

    // Set up row click event to open modal (excluding action buttons)
    const rows = document.querySelectorAll('.data-table tbody tr');
    rows.forEach(row => {
        row.addEventListener('click', (e) => {
            // Don't open modal if clicking on action buttons
            if (!e.target.closest('.action-buttons')) {
                const paymentId = row.querySelector('td:first-child').textContent;
                openPaymentModal(paymentId);
            }
        });
    });

    // Initialize any other payments-specific functionality
}

// Helper function to select elements by containing text
Element.prototype.contains = function(text) {
    return this.textContent.includes(text);
};
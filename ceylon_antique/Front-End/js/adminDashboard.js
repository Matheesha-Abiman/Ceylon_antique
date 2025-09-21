// ../js/adminDashboard.js

// Navigation function
function navigateTo(pagePath) {
    // Simply redirect to the page passed from HTML
    window.location.href = pagePath;
}

document.addEventListener("DOMContentLoaded", function () {
    console.log("✅ Admin Dashboard Loaded");

    // Sidebar active menu highlight
    const menuItems = document.querySelectorAll(".menu-item");

    menuItems.forEach(item => {
        item.addEventListener("click", function () {
            // Remove "active" from all items
            menuItems.forEach(i => i.classList.remove("active"));

            // Add "active" to clicked item
            this.classList.add("active");
        });
    });

    // Search bar functionality
    const searchInput = document.querySelector(".search-bar input");
    if (searchInput) {
        searchInput.addEventListener("keypress", function (e) {
            if (e.key === "Enter") {
                e.preventDefault();
                alert(`🔍 Searching for: ${searchInput.value}`);
            }
        });
    }

    // Notification click
    const notification = document.querySelector(".notification");
    if (notification) {
        notification.addEventListener("click", function () {
            alert("You have 3 new notifications.");
        });
    }

    // Initialize dashboard
    initDashboard();
});

// Dashboard initialization
function initDashboard() {
    console.log("📊 Dashboard initialized");
}

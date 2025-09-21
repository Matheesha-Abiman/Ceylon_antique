// ../js/sidebar.js

// Navigation function
function navigateTo(pagePath) {
    window.location.href = pagePath;
}

document.addEventListener("DOMContentLoaded", function () {
    console.log("✅ Sidebar Loaded");

    const menuItems = document.querySelectorAll(".menu-item");

    // Detect current page name (example: adminDashboard.html)
    const currentPage = window.location.pathname.split("/").pop();

    menuItems.forEach(item => {
        const targetPage = item.getAttribute("onclick")?.match(/'(.*)'/)?.[1];

        // --- Click event for navigation highlight ---
        item.addEventListener("click", function () {
            menuItems.forEach(i => i.classList.remove("active"));
            this.classList.add("active");

            // Save last active page in localStorage
            if (targetPage) {
                localStorage.setItem("activePage", targetPage);
            }
        });

        // --- Auto-active on page load ---
        if (targetPage === currentPage) {
            item.classList.add("active");
        }
    });

    // --- Restore last active menu from localStorage ---
    const savedPage = localStorage.getItem("activePage");
    if (savedPage && savedPage === currentPage) {
        menuItems.forEach(i => {
            const page = i.getAttribute("onclick")?.match(/'(.*)'/)?.[1];
            if (page === savedPage) {
                i.classList.add("active");
            }
        });
    }

    // --- Search bar ---
    const searchInput = document.querySelector(".search-bar input");
    if (searchInput) {
        searchInput.addEventListener("keypress", function (e) {
            if (e.key === "Enter") {
                e.preventDefault();
                alert(`🔍 Searching for: ${searchInput.value}`);
            }
        });
    }

    // --- Notifications ---
    const notification = document.querySelector(".notification");
    if (notification) {
        notification.addEventListener("click", function () {
            alert("You have 3 new notifications.");
        });
    }
});

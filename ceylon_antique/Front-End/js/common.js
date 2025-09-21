const logoutBtn = document.getElementById('logoutBtn');
console.log('logoutBtn working');
if (logoutBtn) {
  logoutBtn.addEventListener('click', function(e) {
    e.preventDefault();

    // Clear cookie (your JWT is stored here)
    document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";

    // Also clear storage just in case
    localStorage.removeItem('token');
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');

    // Redirect to login/index page
    window.location.href = 'index.html';
  });
}

function showLoading(selector) {
  document.querySelector(selector).innerHTML = `
            <div class="spinner-container">
        <div class="spinner"></div>
        <div class="loading-text">Loading...</div>

    </div>
        `;
}

function generateUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    const r = Math.random() * 16 | 0;
    const v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}
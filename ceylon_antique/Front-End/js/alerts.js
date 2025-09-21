// alerts.js
// Common SweetAlert2 functions

// Simple alert
function showAlert(title, text, icon = "info") {
    Swal.fire(title, text, icon);
}

// Success alert
function showSuccess(message) {
    Swal.fire("Success!", message, "success");
}

// Error alert
function showError(message) {
    Swal.fire("Error!", message, "error");
}

// Warning with confirmation
function showConfirm(message, confirmCallback) {
    Swal.fire({
        title: "Are you sure?",
        text: message,
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes",
        cancelButtonText: "Cancel"
    }).then((result) => {
        if (result.isConfirmed && typeof confirmCallback === "function") {
            confirmCallback();
        }
    });
}

// Toast (small top-right popup)
function showToast(message, icon = "success") {
    Swal.fire({
        toast: true,
        position: "top-end",
        icon: icon,
        title: message,
        showConfirmButton: false,
        timer: 2000
    });
}

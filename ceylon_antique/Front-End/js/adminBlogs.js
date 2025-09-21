// Navigation function
function navigateTo(page) {
    if (page === '') {
        window.location.href = '../index.html';
    } else {
        window.location.href = `admin${page.charAt(0).toUpperCase() + page.slice(1)}.html`;
    }
}

// DOM Elements
const addBlogBtn = document.getElementById('addBlogBtn');
const blogModal = document.getElementById('blogModal');
const modalTitle = document.getElementById('modalTitle');
const closeBtn = document.querySelector('.close-btn');
const cancelBtn = document.getElementById('cancelBtn');
const saveBlogBtn = document.getElementById('saveBlogBtn');
const blogStatus = document.getElementById('blogStatus');
const scheduleDateGroup = document.getElementById('scheduleDateGroup');
const editButtons = document.querySelectorAll('.edit-btn');
const deleteButtons = document.querySelectorAll('.delete-btn');
const viewButtons = document.querySelectorAll('.view-btn');
const blogImage = document.getElementById('blogImage');
const imagePreview = document.querySelector('.image-preview');
const tagInput = document.querySelector('.tag-input');
const tagContainer = document.querySelector('.tag-input-container');
const bulkAction = document.getElementById('bulkAction');
const applyBulkAction = document.querySelector('.table-actions .btn-primary');
const editorContent = document.querySelector('.editor-content');
const editorButtons = document.querySelectorAll('.editor-btn');

// Event Listeners
addBlogBtn.addEventListener('click', openAddBlogModal);
closeBtn.addEventListener('click', closeModal);
cancelBtn.addEventListener('click', closeModal);
blogStatus.addEventListener('change', toggleScheduleDate);
blogImage.addEventListener('change', handleImagePreview);
tagInput.addEventListener('keydown', handleTagInput);
applyBulkAction.addEventListener('click', handleBulkAction);

// Editor buttons
editorButtons.forEach(button => {
    button.addEventListener('click', () => {
        const command = button.getAttribute('data-command');
        const value = button.getAttribute('data-value');
        formatText(command, value);
    });
});

// Edit buttons
editButtons.forEach(button => {
    button.addEventListener('click', () => {
        const postId = button.getAttribute('data-id');
        openEditBlogModal(postId);
    });
});

// Delete buttons
deleteButtons.forEach(button => {
    button.addEventListener('click', () => {
        const postId = button.getAttribute('data-id');
        deleteBlogPost(postId);
    });
});

// View buttons
viewButtons.forEach(button => {
    button.addEventListener('click', () => {
        const postId = button.getAttribute('data-id');
        viewBlogPost(postId);
    });
});

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target === blogModal) {
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

    // Initialize blog page functionality
    initBlogPage();
});

// Functions
function openAddBlogModal() {
    modalTitle.textContent = 'Add New Blog Post';
    blogModal.style.display = 'flex';
    // Reset form
    document.getElementById('blogForm').reset();
    editorContent.innerHTML = '';
    clearTags();
    toggleScheduleDate();
    imagePreview.style.display = 'none';
}

function openEditBlogModal(id) {
    modalTitle.textContent = 'Edit Blog Post';
    blogModal.style.display = 'flex';

    // In a real application, you would fetch the blog data by ID
    // and populate the form fields. For this example, we'll use dummy data.
    document.getElementById('blogTitle').value = 'Introduction to Web Development';
    document.getElementById('blogAuthor').value = 'John Smith';
    editorContent.innerHTML = '<p>This is the content of the blog post. Web development is the work involved in developing a website for the Internet or an intranet.</p><p>It can range from developing a simple single static page of plain text to complex web applications, electronic businesses, and social network services.</p>';
    document.getElementById('blogCategory').value = 'technology';

    // Set tags
    clearTags();
    ['web', 'development', 'programming'].forEach(tag => {
        addTag(tag);
    });

    document.getElementById('blogStatus').value = 'published';

    toggleScheduleDate();
    imagePreview.style.display = 'block';
    imagePreview.querySelector('img').src = 'https://images.unsplash.com/photo-1555066931-4365d14bab8c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80';
}

function closeModal() {
    blogModal.style.display = 'none';
}

function toggleScheduleDate() {
    if (blogStatus.value === 'scheduled') {
        scheduleDateGroup.style.display = 'block';
    } else {
        scheduleDateGroup.style.display = 'none';
    }
}

function handleImagePreview(e) {
    const file = e.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            imagePreview.style.display = 'block';
            imagePreview.querySelector('img').src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
}

function handleTagInput(e) {
    if (e.key === 'Enter' && tagInput.value.trim() !== '') {
        e.preventDefault();
        addTag(tagInput.value.trim());
        tagInput.value = '';
    } else if (e.key === 'Backspace' && tagInput.value === '' && tagContainer.querySelectorAll('.tag').length > 0) {
        const tags = tagContainer.querySelectorAll('.tag');
        const lastTag = tags[tags.length - 1];
        lastTag.remove();
    }
}

function addTag(text) {
    const tag = document.createElement('div');
    tag.className = 'tag';
    tag.innerHTML = `
        ${text}
        <span class="tag-remove" onclick="this.parentElement.remove()">×</span>
    `;
    tagContainer.insertBefore(tag, tagInput);
}

function clearTags() {
    const tags = tagContainer.querySelectorAll('.tag');
    tags.forEach(tag => tag.remove());
}

function getTags() {
    const tags = [];
    tagContainer.querySelectorAll('.tag').forEach(tag => {
        tags.push(tag.textContent.replace('×', '').trim());
    });
    return tags;
}

function formatText(command, value = null) {
    document.execCommand(command, false, value);
    editorContent.focus();
}

function deleteBlogPost(id) {
    if (confirm('Are you sure you want to delete this blog post?')) {
        // In a real application, you would send a delete request to the server
        alert(`Blog post with ID ${id} has been deleted.`);
        // Remove the row from the table
        const row = document.querySelector(`.edit-btn[data-id="${id}"]`).closest('tr');
        row.remove();
    }
}

function viewBlogPost(id) {
    // In a real application, you would redirect to the blog post or open a preview
    alert(`Viewing blog post with ID ${id}`);
}

function handleBulkAction() {
    const action = bulkAction.value;
    if (!action) {
        alert('Please select a bulk action');
        return;
    }

    const selectedRows = document.querySelectorAll('.row-checkbox:checked');
    if (selectedRows.length === 0) {
        alert('Please select at least one blog post');
        return;
    }

    if (action === 'delete') {
        if (confirm(`Are you sure you want to delete ${selectedRows.length} blog post(s)?`)) {
            selectedRows.forEach(checkbox => {
                const row = checkbox.closest('tr');
                row.remove();
            });
            alert(`${selectedRows.length} blog post(s) deleted successfully.`);
        }
    } else {
        selectedRows.forEach(checkbox => {
            const row = checkbox.closest('tr');
            const statusCell = row.querySelector('.status');

            if (action === 'publish') {
                statusCell.textContent = 'Published';
                statusCell.className = 'status published';
            } else if (action === 'draft') {
                statusCell.textContent = 'Draft';
                statusCell.className = 'status draft';
            }
        });
        alert(`${selectedRows.length} blog post(s) updated successfully.`);
    }

    // Reset bulk action select
    bulkAction.value = '';
}

function saveBlogPost() {
    // Get form values
    const title = document.getElementById('blogTitle').value;
    const author = document.getElementById('blogAuthor').value;
    const content = editorContent.innerHTML;
    const category = document.getElementById('blogCategory').value;
    const tags = getTags();
    const status = document.getElementById('blogStatus').value;
    const scheduleDate = document.getElementById('blogScheduleDate').value;
    const imageFile = document.getElementById('blogImage').files[0];

    // Basic validation
    if (!title || !author || !content || !category) {
        alert('Please fill in all required fields');
        return;
    }

    // In a real application, you would send this data to the server
    console.log('Saving blog post:', {
        title,
        author,
        content,
        category,
        tags,
        status,
        scheduleDate,
        imageFile: imageFile ? imageFile.name : 'No file selected'
    });

    alert('Blog post saved successfully!');
    closeModal();

    // If we're adding a new post, add it to the table
    if (modalTitle.textContent === 'Add New Blog Post') {
        addPostToTable({
            id: Date.now(),
            title,
            author,
            category,
            tags: tags.join(', '),
            date: new Date().toLocaleDateString(),
            status
        });
    }
}

function addPostToTable(post) {
    const tbody = document.querySelector('.data-table tbody');
    const tr = document.createElement('tr');

    tr.innerHTML = `
        <td>#BLOG-${post.id}</td>
        <td>${post.title}</td>
        <td>${post.author}</td>
        <td>${post.category}</td>
        <td>${post.tags}</td>
        <td>${post.date}</td>
        <td><span class="status ${post.status}">${post.status.charAt(0).toUpperCase() + post.status.slice(1)}</span></td>
        <td>
            <div class="action-buttons">
                <div class="action-btn edit-btn" data-id="${post.id}">
                    <i class="fas fa-edit"></i>
                </div>
                <div class="action-btn delete-btn" data-id="${post.id}">
                    <i class="fas fa-trash"></i>
                </div>
                <div class="action-btn view-btn" data-id="${post.id}">
                    <i class="fas fa-eye"></i>
                </div>
            </div>
        </td>
    `;

    tbody.appendChild(tr);

    // Add event listeners to the new buttons
    tr.querySelector('.edit-btn').addEventListener('click', () => {
        openEditBlogModal(post.id);
    });

    tr.querySelector('.delete-btn').addEventListener('click', () => {
        deleteBlogPost(post.id);
    });

    tr.querySelector('.view-btn').addEventListener('click', () => {
        viewBlogPost(post.id);
    });
}

// Blog page initialization
function initBlogPage() {
    console.log('Blog page initialized');

    // Set up save button event listener
    saveBlogBtn.addEventListener('click', saveBlogPost);

    // Set up select all checkbox functionality
    const selectAll = document.getElementById('selectAll');
    const rowCheckboxes = document.querySelectorAll('.row-checkbox');

    selectAll.addEventListener('change', () => {
        rowCheckboxes.forEach(checkbox => {
            checkbox.checked = selectAll.checked;
        });
    });

    // Initialize any other blog-specific functionality
}
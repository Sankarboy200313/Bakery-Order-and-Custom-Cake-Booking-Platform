document.addEventListener('DOMContentLoaded', () => {
    const userForm = document.getElementById('userForm');
    const adminForm = document.getElementById('adminForm');
    const adminTable = document.getElementById('adminTable');

    // Add User
    userForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const data = {
            action: 'addUser',
            userId: userForm.userId.value,
            username: userForm.username.value,
            fullName: userForm.fullName.value,
            email: userForm.email.value
        };

        fetch('/users', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        }).then(() => location.reload());
    });

    // Delete User
    document.getElementById('deleteUserBtn').addEventListener('click', () => {
        const userId = document.getElementById('deleteUserId').value;

        fetch(`/users/${userId}`, {
            method: 'DELETE'
        }).then(() => location.reload());
    });

    // Add Admin
    adminForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const data = {
            action: 'addAdmin',
            adminId: adminForm.adminId.value,
            username: adminForm.adminUsername.value,
            password: adminForm.adminPassword.value
        };

        fetch('/admins', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        }).then(() => location.reload());
    });

    // Delete Admin
    document.getElementById('deleteAdminBtn').addEventListener('click', () => {
        const adminId = document.getElementById('deleteAdminId').value;

        fetch(`/admins/${adminId}`, {
            method: 'DELETE'
        }).then(() => location.reload());
    });

    // Edit Admin (triggered by clicking edit)
    window.editAdmin = function (adminId, username, password) {
        const newUsername = prompt("Enter new username", username);
        const newPassword = prompt("Enter new password", password);

        if (newUsername && newPassword) {
            fetch(`/admins/${adminId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username: newUsername, password: newPassword })
            }).then(() => location.reload());
        }
    };
});

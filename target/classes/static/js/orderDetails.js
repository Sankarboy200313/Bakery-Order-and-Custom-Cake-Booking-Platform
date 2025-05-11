function showConfirmation() {
    document.getElementById('confirmPopup').style.display = 'flex';
}

function hideConfirmation() {
    document.getElementById('confirmPopup').style.display = 'none';
}
function showConfirmation() {
    document.getElementById('confirmPopup').style.display = 'flex';
}

function hideConfirmation() {
    document.getElementById('confirmPopup').style.display = 'none';
}

function showCancelSuccessMessageAndSubmit() {
    // Hide confirmation popup
    document.getElementById('confirmPopup').style.display = 'none';

    // Show success popup
    const successPopup = document.getElementById('successPopup');
    successPopup.style.display = 'flex';

    // Wait 2 seconds, then submit the delete form
    setTimeout(() => {
        document.getElementById('deleteForm').submit();
    }, 2000);
}


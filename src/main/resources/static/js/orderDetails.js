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
    document.getElementById('confirmPopup').style.display = 'none';

    const successPopup = document.getElementById('successPopup');
    successPopup.style.display = 'flex';

    setTimeout(() => {
        document.getElementById('deleteForm').submit();
    }, 2000);
}


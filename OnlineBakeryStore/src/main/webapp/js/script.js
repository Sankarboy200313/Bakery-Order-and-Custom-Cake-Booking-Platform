// Form validation for order form
document.addEventListener('DOMContentLoaded', function() {
    const orderForm = document.querySelector('form[action="order"]');
    if (orderForm) {
        orderForm.addEventListener('submit', function(e) {
            const deliveryDate = document.getElementById('deliveryDate');
            const today = new Date().toISOString().split('T')[0];

            if (deliveryDate.value < today) {
                alert('Delivery date cannot be in the past');
                e.preventDefault();
            }
        });
    }
});

// Modal functionality for admin dashboard
function viewOrderDetails(orderId) {
    fetch('orderDetails.jsp?orderId=' + orderId)
        .then(response => response.text())
        .then(html => {
            document.getElementById('orderDetailsContent').innerHTML = html;
            document.getElementById('orderDetailsModal').style.display = 'block';
        });
}

document.querySelector('.close').addEventListener('click', function() {
    document.getElementById('orderDetailsModal').style.display = 'none';
});

window.addEventListener('click', function(event) {
    const modal = document.getElementById('orderDetailsModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});
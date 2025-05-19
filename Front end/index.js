document.getElementById('cakeForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const flavor = document.getElementById('flavor').value;
    const shape = document.getElementById('shape').value;
    const message = document.getElementById('message').value;

    const toppingCheckboxes = document.querySelectorAll('input[name="toppings"]:checked');
    const toppings = Array.from(toppingCheckboxes).map(cb => cb.value).join(',');

    // Create the order object
    const order = {
        flavor: flavor,
        shape: shape,
        toppings: toppings,
        message: message
    };

    // Send the order to the backend
    fetch('http://localhost:8080/api/custom-cakes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(order)
    })
    .then(response => response.json())
    .then(data => {
        // Redirect to summary page with the order ID
        window.location.href = `summary.html?orderId=${data.id}`;
    })
    .catch(error => {
        console.error('Error:', error);
        alert('There was an error placing your order. Please try again.');
    });
});
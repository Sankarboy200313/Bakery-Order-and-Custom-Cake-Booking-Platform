// Fetch all products
fetch('http://localhost:8080/api/products')
  .then(response => response.json())
  .then(products => {
    // Update your UI with the products
  });

// Add a product to cart
function addToCart(productId, quantity) {
  fetch(`http://localhost:8080/api/products/${productId}`)
    .then(response => response.json())
    .then(product => {
      // Add to cart logic
    });
}
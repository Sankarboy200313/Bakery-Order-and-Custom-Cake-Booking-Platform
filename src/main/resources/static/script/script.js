async function loadProducts() {
    const res = await fetch('http://localhost:8080/api/products');
    const data = await res.json();

    ['bakery', 'cake'].forEach(category => {
        const list = document.getElementById(`${category}-stock-list`);
        const count = document.getElementById(`${category}-stock-count`);
        list.innerHTML = '';
        let productCount = 0;

        data.forEach(p => {
            if (p.category === category) {
                productCount++;
                const li = document.createElement('li');
                li.textContent = `${p.id} - ${p.name} (Rs.${p.price})`;
                list.appendChild(li);
            }
        });
        count.textContent = `Total ${category} items: ${productCount}`;
    });
}

async function addProduct(category) {
    const id = document.getElementById(`${category}-product-id`).value.trim();
    const name = document.getElementById(`${category}-product-name`).value.trim();
    const price = document.getElementById(`${category}-product-price`).value.trim();

    if (!id || !name || !price) {
        alert("Please fill all fields");
        return;
    }

    await fetch('http://localhost:8080/api/products', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({id, name, price, category})
    });

    loadProducts();
}

async function removeProduct(category) {
    const id = document.getElementById(`remove-${category}-product-id`).value.trim();
    if (!id) return alert("Please enter ID");

    await fetch(`http://localhost:8080/api/products/${id}`, {
        method: 'DELETE'
    });

    loadProducts();
}

window.onload = loadProducts;

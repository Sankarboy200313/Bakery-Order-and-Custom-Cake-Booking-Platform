fetch('/api/order-management/data')
    .then(response => response.json())
    .then(data => {
        const dates = data.map(d => d.date);
        const revenues = data.map(d => d.revenue);

        // Process data to get orders by month
        const ordersByMonth = {};
        data.forEach(order => {
            // Extract month from date (assuming date format is YYYY-MM-DD)
            const orderDate = new Date(order.date);
            const month = orderDate.toLocaleString('default', { month: 'long' });

            if (!ordersByMonth[month]) {
                ordersByMonth[month] = 0;
            }
            // Assuming each entry represents one order, increase count
            ordersByMonth[month]++;
        });

        // Convert to arrays for Plotly
        const months = Object.keys(ordersByMonth);
        const orderCounts = Object.values(ordersByMonth);

        // Orders chart - modified to show count by month
        Plotly.newPlot('orderChart', [{
            x: months,
            y: orderCounts,
            type: 'bar',
            marker: { color: '#ff6f61' }
        }], {
            title: 'Cake Orders by Month',
            xaxis: { title: 'Month' },
            yaxis: { title: 'Number of Orders' }
        });

        // Revenue line chart - keep as is
        Plotly.newPlot('revenueChart', [{
            x: dates,
            y: revenues,
            type: 'scatter',
            mode: 'lines+markers',
            line: { color: '#ff6f61' }
        }], {
            title: 'Revenue Trend',
            xaxis: { title: 'Date' },
            yaxis: { title: 'Revenue (LKR)' }
        });
    })
    .catch(error => {
        console.error('Error loading chart data:', error);
        document.getElementById('orderChart').innerHTML = '<p class="error-message">Failed to load order data</p>';
        document.getElementById('revenueChart').innerHTML = '<p class="error-message">Failed to load revenue data</p>';
    });
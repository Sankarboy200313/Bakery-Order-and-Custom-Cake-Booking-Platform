<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
  <h1>Order Queue Management</h1>

  <table class="order-table">
    <thead>
    <tr>
      <th>Order ID</th>
      <th>Customer</th>
      <th>Product</th>
      <th>Order Date</th>
      <th>Delivery Date</th>
      <th>Status</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
      <tr>
        <td>${order.id}</td>
        <td>${order.customerName}</td>
        <td>${order.product}</td>
        <td>${order.orderDate}</td>
        <td>${order.deliveryDate} ${order.deliveryTime}</td>
        <td class="status-${order.status.toLowerCase()}">${order.status}</td>
        <td class="actions">
          <form action="admin" method="post" style="display: inline;">
            <input type="hidden" name="orderId" value="${order.id}">
            <select name="status" onchange="this.form.submit()">
              <option value="Pending" ${order.status == 'Pending' ? 'selected' : ''}>Pending</option>
              <option value="Baking" ${order.status == 'Baking' ? 'selected' : ''}>Baking</option>
              <option value="Ready" ${order.status == 'Ready' ? 'selected' : ''}>Ready</option>
            </select>
            <input type="hidden" name="action" value="update">
          </form>

          <form action="admin" method="post" style="display: inline;">
            <input type="hidden" name="orderId" value="${order.id}">
            <input type="hidden" name="action" value="complete">
            <button type="submit">Complete</button>
          </form>

          <button onclick="viewOrderDetails('${order.id}')">Details</button>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <div id="orderDetailsModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <div id="orderDetailsContent"></div>
    </div>
  </div>
</div>

<script src="js/script.js"></script>
</body>
</html>
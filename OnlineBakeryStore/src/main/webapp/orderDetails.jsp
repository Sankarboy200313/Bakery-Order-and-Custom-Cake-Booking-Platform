<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Order Details</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
  <h1>Order Details</h1>

  <div class="order-details">
    <p><strong>Order ID:</strong> ${order.id}</p>
    <p><strong>Status:</strong>
      <span class="status-${order.status.toLowerCase()}">${order.status}</span>
    </p>
    <p><strong>Order Date:</strong> ${order.orderDate}</p>

    <h2>Customer Information</h2>
    <p><strong>Name:</strong> ${order.customerName}</p>
    <p><strong>Email:</strong> ${order.email}</p>
    <p><strong>Phone:</strong> ${order.phone}</p>

    <h2>Order Information</h2>
    <p><strong>Product:</strong> ${order.product}</p>
    <c:if test="${not empty order.specialRequests}">
      <p><strong>Special Requests:</strong> ${order.specialRequests}</p>
    </c:if>

    <h2>Delivery Information</h2>
    <p><strong>Date:</strong> ${order.deliveryDate}</p>
    <p><strong>Time:</strong> ${order.deliveryTime}</p>
    <p><strong>Address:</strong> ${order.address}</p>
  </div>

  <a href="admin">Back to Dashboard</a>
</div>
</body>
</html>
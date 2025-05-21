<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Track Your Order</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
  <h1>Order Tracking</h1>

  <c:choose>
    <c:when test="${not empty order }">
      <div class="order-details">
        <p><strong>Order ID:</strong> ${order.id}</p>
        <p><strong>Status:</strong>
          <span class="status-${order.status.toLowerCase()}">${order.status}</span>
        </p>
        <p><strong>Customer:</strong> ${order.customerName}</p>
        <p><strong>Product:</strong> ${order.product}</p>
        <p><strong>Delivery Date:</strong> ${order.deliveryDate} at ${order.deliveryTime}</p>

        <c:if test="${not empty order.specialRequests}">
          <p><strong>Special Requests:</strong> ${order.specialRequests}</p>
        </c:if>
      </div>
    </c:when>
    <c:otherwise>
      <div class="alert alert-info">
        <p>Order not found. Please check your order ID.</p>
        <a href="order">Place a new order</a>
      </div>
    </c:otherwise>
  </c:choose>

  <div class="track-another">
    <form action="track-order" method="get">
      <label for="orderId">Track another order:</label>
      <input type="text" id="orderId" name="orderId" placeholder="Enter your order ID">
      <button type="submit">Track</button>
    </form>
  </div>
</div>
</body>
</html>
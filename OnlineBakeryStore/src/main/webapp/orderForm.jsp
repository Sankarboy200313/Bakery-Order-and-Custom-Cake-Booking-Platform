<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Bakery Order Form</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
  <h1>BAKERY ORDER FORM</h1>

  <form action="order" method="post">
    <h2>Customer Information</h2>
    <div class="form-group">
      <label for="name">Name:</label>
      <input type="text" id="name" name="name" required>
    </div>

    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required>
    </div>

    <div class="form-group">
      <label for="phone">Phone Number:</label>
      <input type="tel" id="phone" name="phone"
             pattern="[0-9]{10}"
             maxlength="10"
             title="Please enter a 10-digit phone number (numbers only)"
             oninput="this.value=this.value.replace(/[^0-9]/g,'');"
             required>
    </div>
    <h2>Order Details</h2>
    <div class="form-group">
      <label for="product">Select Product:</label>
      <select id="product" name="product" required>
        <option value="">- Select a product -</option>
        <option value="Birthday Cake">Birthday Cake</option>
        <option value="Wedding Cake">Wedding Cake</option>
        <option value="Cupcakes (Dozen)">Cupcakes (Dozen)</option>
        <option value="Cookies (12pcs)">Cookies (12pcs)</option>
        <option value="Bread Loaf">Bread Loaf</option>
      </select>
    </div>

    <div class="form-group">
      <label for="specialRequests">Special Requests:</label>
      <textarea id="specialRequests" name="specialRequests" rows="3"></textarea>
    </div>

    <h2>Delivery Info</h2>
    <div class="form-group">
      <label for="deliveryDate">Date:</label>
      <input type="date" id="deliveryDate" name="deliveryDate" required>
    </div>

    <div class="form-group">
      <label for="deliveryTime">Time:</label>
      <input type="time" id="deliveryTime" name="deliveryTime" required>
    </div>

    <div class="form-group">
      <label for="address">Address:</label>
      <textarea id="address" name="address" rows="2" required></textarea>
    </div>

    <div class="form-actions">
      <button type="submit">SUBMIT ORDER</button>
      <button type="reset">RESET</button>
    </div>
  </form>
</div>

<script src="js/script.js"></script>
</body>
</html>
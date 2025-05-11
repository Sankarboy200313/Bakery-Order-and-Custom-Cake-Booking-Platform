<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Pickup</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-cream-light min-h-screen flex items-center justify-center p-6">
<div class="bg-cream rounded-2xl shadow-2xl p-10 w-full max-w-xl">
    <h1 class="text-3xl font-bold text-center text-brown-dark mb-8">Create a Pickup</h1>

    <form id="pickupForm" class="space-y-6">
        <input type="text" id="pickupPersonName" name="pickupPersonName" placeholder="Full Name" required
               class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">

        <input type="text" id="pickupLocation" name="pickupLocation" placeholder="Pickup Location" required
               class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">

        <input type="datetime-local" id="pickupDate" name="pickupDate" required
               class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">

        <input type="text" id="contactNumber" name="contactNumber" placeholder="Contact Number" required
               class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">

        <input type="email" id="email" name="email" placeholder="Email Address" required
               class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">

        <select id="status" name="status"
                class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">
            <option value="Pending">Pending</option>
            <option value="Confirmed">Confirmed</option>
            <option value="Completed">Completed</option>
        </select>

        <button type="submit"
                class="w-full bg-brown hover:bg-brown-dark text-white font-semibold py-3 rounded-xl transition duration-200">
            Create Pickup
        </button>
    </form>

    <div id="message" class="mt-6 text-center text-sm font-medium"></div>
</div>

<script>
    document.getElementById("pickupForm").addEventListener("submit", async function (e) {
        e.preventDefault();

        const data = {
            pickupPersonName: document.getElementById("pickupPersonName").value,
            pickupLocation: document.getElementById("pickupLocation").value,
            pickupDate: new Date(document.getElementById("pickupDate").value).toISOString(),
            contactNumber: document.getElementById("contactNumber").value,
            email: document.getElementById("email").value,
            status: document.getElementById("status").value
        };

        const response = await fetch("http://localhost:8080/api/pickups", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        const messageEl = document.getElementById("message");

        if (response.status === 201) {
            messageEl.textContent = "✅ Pickup created successfully!";
            messageEl.className = "text-green-600 mt-6 text-center";
            document.getElementById("pickupForm").reset();
        } else {
            messageEl.textContent = "❌ Failed to create pickup.";
            messageEl.className = "text-red-600 mt-6 text-center";
        }
    });
</script>

<style>
    .bg-cream-light {
        background-color: #fdf6e3;
    }

    .bg-cream {
        background-color: #fef3c7;
    }

    .text-brown-dark {
        color: #5c3a21;
    }

    .border-brown {
        border-color: #a77945;
    }

    .focus\:ring-brown-light:focus {
        --tw-ring-color: #d6a77a;
    }

    .bg-brown {
        background-color: #a77945;
    }

    .bg-brown-dark {
        background-color: #895f32;
    }
</style>
</body>
</html>

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

    <form id="pickupForm" class="space-y-6" novalidate>
        <div>
            <input type="text" id="pickupPersonName" name="pickupPersonName" placeholder="Full Name" required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div>
            <input type="text" id="pickupLocation" name="pickupLocation" placeholder="Pickup Location" required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div>
            <input type="datetime-local" id="pickupDate" name="pickupDate" required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div>
            <input type="text" id="contactNumber" name="contactNumber" placeholder="Contact Number" required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div>
            <input type="email" id="email" name="email" placeholder="Email Address" required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div>
            <select id="status" name="status"
                    class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light">
                <option value="Pending">Pending</option>
                <option value="Confirmed">Confirmed</option>
                <option value="Completed">Completed</option>
            </select>
        </div>

        <button type="submit"
                class="w-full bg-brown hover:bg-brown-dark text-white font-semibold py-3 rounded-xl transition duration-200">
            Create Pickup
        </button>
    </form>

    <div id="message" class="mt-6 text-center text-sm font-medium"></div>
</div>

<script>
    const form = document.getElementById("pickupForm");
    const messageEl = document.getElementById("message");

    function showError(input, message) {
        input.classList.add("border-red-500");
        const errorEl = input.nextElementSibling;
        if (errorEl) errorEl.textContent = message;
    }

    function clearError(input) {
        input.classList.remove("border-red-500");
        const errorEl = input.nextElementSibling;
        if (errorEl) errorEl.textContent = "";
    }

    function validateField(id, regex, errorMsg, customFn = null) {
        const input = document.getElementById(id);
        input.addEventListener("input", () => {
            if (customFn) {
                const valid = customFn(input.value);
                valid ? clearError(input) : showError(input, errorMsg);
            } else {
                regex.test(input.value) ? clearError(input) : showError(input, errorMsg);
            }
        });
    }

    // Real-time validations
    validateField("pickupPersonName", /^[A-Za-z\s]+$/, "❌ Name should only contain letters");
    validateField("pickupLocation", /^[A-Za-z\s,]+$/, "❌ Location should not contain numbers or symbols");
    validateField("contactNumber", /^[0-9]{10}$/, "❌ Enter exactly 10 digits");
    validateField("email", /^[^\s@]+@[^\s@]+\.[^\s@]+$/, "❌ Enter a valid email address");
    validateField("pickupDate", null, "❌ Date must be in the future", val => new Date(val) > new Date());

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        let hasError = false;
        form.querySelectorAll("input").forEach(input => {
            input.dispatchEvent(new Event("input"));
            const errorText = input.nextElementSibling?.textContent;
            if (errorText) hasError = true;
        });

        if (hasError) {
            messageEl.textContent = "❌ Please fix the validation errors before submitting.";
            messageEl.className = "text-red-600 mt-6 text-center";
            return;
        }

        const data = {
            pickupPersonName: document.getElementById("pickupPersonName").value.trim(),
            pickupLocation: document.getElementById("pickupLocation").value.trim(),
            pickupDate: new Date(document.getElementById("pickupDate").value).toISOString(),
            contactNumber: document.getElementById("contactNumber").value.trim(),
            email: document.getElementById("email").value.trim(),
            status: document.getElementById("status").value
        };

        const response = await fetch("http://localhost:8080/api/pickups", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (response.status === 201) {
            messageEl.textContent = "✅ Pickup created successfully!";
            messageEl.className = "text-green-600 mt-6 text-center";
            form.reset();
        } else {
            messageEl.textContent = "❌ Failed to create pickup.";
            messageEl.className = "text-red-600 mt-6 text-center";
        }
    });
</script>

<style>
    .bg-cream-light {
        background-color: #fdf6e4;
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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Pickup</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-cream-light min-h-screen flex items-center justify-center p-6">
<div class="bg-cream rounded-2xl shadow-2xl p-10 w-full max-w-xl">
    <h1 class="text-3xl font-bold text-center text-brown-dark mb-8">Edit Pickup</h1>

    <!-- Status message area -->
    <div id="statusMessage" class="mb-4 hidden text-center text-sm font-medium">
        <p id="successMessage" class="text-green-600 hidden"></p>
        <p id="errorMessage" class="text-red-600 hidden"></p>
    </div>

    <form id="editPickupForm" class="space-y-6" novalidate>
        <input type="hidden" id="pickupId" value="${pickupModel.id}" />

        <div>
            <input type="text" id="pickupPersonName" name="pickupPersonName" value="${pickupModel.pickupPersonName}"
                   placeholder="Full Name" required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light" />
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div>
            <input type="text" id="pickupLocation" name="pickupLocation" value="${pickupModel.pickupLocation}"
                   placeholder="Pickup Location" required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light" />
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div>
            <input type="datetime-local" id="pickupDate" name="pickupDate" value="${pickupModel.pickupDate}"
                   required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light" />
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div>
            <input type="text" id="contactNumber" name="contactNumber" value="${pickupModel.contactNumber}"
                   placeholder="Contact Number" required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light" />
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div>
            <input type="email" id="email" name="email" value="${pickupModel.email}"
                   placeholder="Email Address" required
                   class="w-full p-3 border border-brown rounded-xl focus:outline-none focus:ring-2 focus:ring-brown-light" />
            <div class="text-sm text-red-600 mt-1"></div>
        </div>

        <div class="flex items-center justify-between pt-4 space-x-2">
            <button type="submit"
                    class="w-full bg-brown hover:bg-brown-dark text-white font-semibold py-3 rounded-xl transition duration-200">
                Update Pickup
            </button>
            <a href="${pageContext.request.contextPath}/"
               class="w-full text-center bg-gray-200 hover:bg-gray-300 text-brown-dark font-semibold py-3 rounded-xl transition duration-200">
                Back
            </a>
        </div>
    </form>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const form = document.getElementById('editPickupForm');
        const statusMessage = document.getElementById('statusMessage');
        const successMessage = document.getElementById('successMessage');
        const errorMessage = document.getElementById('errorMessage');

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

        // Real-time validation setup
        validateField("pickupPersonName", /^[A-Za-z\s]+$/, "❌ Name should only contain letters");
        validateField("pickupLocation", /^[A-Za-z\s,]+$/, "❌ Location should not contain numbers or symbols");
        validateField("contactNumber", /^[0-9]{10}$/, "❌ Enter exactly 10 digits");
        validateField("email", /^[^\s@]+@[^\s@]+\.[^\s@]+$/, "❌ Enter a valid email address");
        validateField("pickupDate", null, "❌ Pickup date must be in the future", val => new Date(val) > new Date());

        form.addEventListener('submit', function (e) {
            e.preventDefault();

            let hasError = false;
            form.querySelectorAll("input").forEach(input => {
                input.dispatchEvent(new Event("input"));
                const errorText = input.nextElementSibling?.textContent;
                if (errorText) hasError = true;
            });

            if (hasError) {
                errorMessage.textContent = "❌ Please fix validation errors before submitting.";
                errorMessage.classList.remove('hidden');
                successMessage.classList.add('hidden');
                statusMessage.classList.remove('hidden');
                return;
            }

            const pickupId = document.getElementById('pickupId').value;
            const data = {
                id: pickupId,
                pickupPersonName: document.getElementById('pickupPersonName').value.trim(),
                pickupLocation: document.getElementById('pickupLocation').value.trim(),
                pickupDate: new Date(document.getElementById('pickupDate').value).toISOString(),
                contactNumber: document.getElementById('contactNumber').value.trim(),
                email: document.getElementById('email').value.trim()
            };

            fetch(`${pageContext.request.contextPath}/api/pickups/id/` + pickupId, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            })
                .then(response => {
                    statusMessage.classList.remove('hidden');
                    if (response.ok) {
                        successMessage.textContent = "✅ Pickup updated successfully!";
                        successMessage.classList.remove('hidden');
                        errorMessage.classList.add('hidden');
                    } else {
                        throw new Error("Failed to update pickup.");
                    }
                })
                .catch(error => {
                    statusMessage.classList.remove('hidden');
                    errorMessage.textContent = "❌ " + error.message;
                    errorMessage.classList.remove('hidden');
                    successMessage.classList.add('hidden');
                });
        });
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

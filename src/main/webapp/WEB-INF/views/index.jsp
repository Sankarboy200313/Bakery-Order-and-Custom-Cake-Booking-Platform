<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Pickups</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
</head>
<body class="bg-[#fdf6e3] min-h-screen p-6 font-sans">
<div class="max-w-7xl mx-auto">
    <div class="flex flex-col md:flex-row justify-between items-center mb-6 gap-4">
        <h1 class="text-3xl font-bold text-[#3b3b3b]">Pickup Records</h1>
        <div class="flex gap-2">
            <input
                    type="text"
                    id="searchInput"
                    placeholder="Search by name/location..."
                    class="border border-[#e6ddc4] px-3 py-2 rounded-md"
                    oninput="filterTable()"
            />
            <button
                    onclick="window.location.href='/create'"
                    class="bg-[#f5c88f] px-4 py-2 rounded hover:bg-[#f3b76d]"
            >
                + Create Pickup
            </button>
            <button
                    onclick="exportPDF()"
                    class="bg-[#c8d5a6] px-4 py-2 rounded hover:bg-[#aecd86]"
            >
                Export PDF
            </button>
        </div>
    </div>

    <div id="loading" class="text-[#3b3b3b] text-lg">Loading pickups...</div>
    <div class="overflow-x-auto">
        <table id="pickupTable" class="min-w-full text-sm hidden">
            <thead class="bg-[#f3e8d5] text-[#3b3b3b]">
            <tr>
                <th class="px-4 py-2 text-left">Name</th>
                <th class="px-4 py-2 text-left">Location</th>
                <th class="px-4 py-2 text-left">Pickup Date</th>
                <th class="px-4 py-2 text-left">Status</th>
                <th class="px-4 py-2 text-left">Email</th>
                <th class="px-4 py-2 text-left">Contact</th>
                <th class="px-4 py-2 text-left">Actions</th>
            </tr>
            </thead>
            <tbody id="tableBody" class="bg-white text-[#3b3b3b]"></tbody>
        </table>
    </div>
</div>

<script>
    var loading = document.getElementById("loading");
    var table = document.getElementById("pickupTable");
    var tableBody = document.getElementById("tableBody");
    var searchInput = document.getElementById("searchInput");
    var pickups = [];

    fetch("/api/pickups")
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            pickups = data;
            loading.style.display = "none";
            table.classList.remove("hidden");
            renderTable(data);
        })
        .catch(function (error) {
            loading.innerText = "Failed to load pickups.";
            console.error(error);
        });

    function renderTable(data) {
        tableBody.innerHTML = "";
        data.forEach(function (pickup) {
            var row = document.createElement("tr");

            row.innerHTML =
                "<td class='border px-4 py-2'>" + pickup.pickupPersonName + "</td>" +
                "<td class='border px-4 py-2'>" + pickup.pickupLocation + "</td>" +
                "<td class='border px-4 py-2'>" +
                new Date(pickup.pickupDate).toLocaleString() +
                "</td>" +
                "<td class='border px-4 py-2'>" + pickup.status + "</td>" +
                "<td class='border px-4 py-2'>" + pickup.email + "</td>" +
                "<td class='border px-4 py-2'>" + pickup.contactNumber + "</td>" +
                "<td class='border px-4 py-2'>" +
                "<button onclick=\"window.location.href='/edit/" +
                pickup.id +
                "'\" class='bg-[#f5c88f] text-sm px-3 py-1 rounded hover:bg-[#f3b76d] mr-2'>Edit</button>" +
                "<button onclick=\"deletePickup('" +
                pickup.id +
                "')\" class='bg-red-400 text-white text-sm px-3 py-1 rounded hover:bg-red-500'>Delete</button>" +
                "</td>";

            tableBody.appendChild(row);
        });
    }

    function filterTable() {
        var keyword = searchInput.value.toLowerCase();
        var filtered = pickups.filter(function (p) {
            return (
                p.pickupPersonName.toLowerCase().includes(keyword) ||
                p.pickupLocation.toLowerCase().includes(keyword)
            );
        });
        renderTable(filtered);
    }

    function deletePickup(id) {
        var confirmDelete = confirm("Are you sure you want to delete this pickup?");
        if (!confirmDelete) return;

        fetch("/api/pickups/id/" + id, {
            method: "DELETE"
        })
            .then(function (response) {
                if (response.ok) {
                    pickups = pickups.filter(function (p) {
                        return p.id !== id;
                    });
                    filterTable();
                    alert("Pickup deleted successfully.");
                } else {
                    alert("Failed to delete pickup.");
                }
            })
            .catch(function (error) {
                alert("Error deleting pickup.");
                console.error(error);
            });
    }

    function exportPDF() {
        var { jsPDF } = window.jspdf;
        var doc = new jsPDF();
        doc.text("Pickup Records", 14, 16);
        var rows = pickups.map(function (p) {
            return [
                p.pickupPersonName,
                p.pickupLocation,
                new Date(p.pickupDate).toLocaleString(),
                p.status,
                p.email,
                p.contactNumber
            ];
        });

        doc.autoTable({
            head: [["Name", "Location", "Pickup Date", "Status", "Email", "Contact"]],
            body: rows,
            startY: 20
        });

        doc.save("pickups.pdf");
    }
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.29/jspdf.plugin.autotable.min.js"></script>
</body>
</html>

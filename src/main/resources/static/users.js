document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("userForm");
    let currentStep = 1;

    const step1 = document.getElementById("step1");
    const step2 = document.getElementById("step2");
    const step3 = document.getElementById("step3");

    const nextBtn = document.getElementById("nextStepBtn");
    const prevBtn = document.getElementById("prevStepBtn");
    const submitBtn = document.getElementById("submitBtn");

    const customerSelect = document.getElementById("customerSelect");
    const employeeSelect = document.getElementById("employeeSelect");
    const customerOptions = document.getElementById("customerOptions");
    const employeeOptions = document.getElementById("employeeOptions");
    const createNewCustomer = document.getElementById("createNewCustomer");
    const createNewEmployee = document.getElementById("createNewEmployee");

    populateSelect("/api/customers", "customerSelect", "name", "id", "-- Select Customer --");
    populateSelect("/api/employees", "employeeSelect", "fullName", "id", "-- Select Employee --");

    nextBtn.addEventListener("click", () => {
        if (currentStep === 1) {
            step1.classList.add("d-none");
            step2.classList.remove("d-none");
            prevBtn.classList.remove("d-none");

            const type = getSelectedUserType();
            if (type === "customer") {
                customerOptions.classList.remove("d-none");
                employeeOptions.classList.add("d-none");
            } else {
                employeeOptions.classList.remove("d-none");
                customerOptions.classList.add("d-none");
            }

            currentStep = 2;
        } else if (currentStep === 2) {
            const type = getSelectedUserType();
            const isNew = type === "customer" ? createNewCustomer.checked : createNewEmployee.checked;

            step2.classList.add("d-none");
            step3.classList.remove("d-none");
            nextBtn.classList.add("d-none");
            submitBtn.classList.remove("d-none");

            currentStep = 3;
        }
    });

    prevBtn.addEventListener("click", () => {
        if (currentStep === 2) {
            step2.classList.add("d-none");
            step1.classList.remove("d-none");
            prevBtn.classList.add("d-none");
            currentStep = 1;
        } else if (currentStep === 3) {
            step3.classList.add("d-none");
            step2.classList.remove("d-none");
            nextBtn.classList.remove("d-none");
            submitBtn.classList.add("d-none");
            currentStep = 2;
        }
    });

    function getSelectedUserType() {
        return document.querySelector('input[name="userType"]:checked').value;
    }

    // Populate helper
    async function populateSelect(url, selectId, labelAttr, valueAttr, placeholder) {
        const select = document.getElementById(selectId);
        select.innerHTML = `<option value="">${placeholder}</option>`;
        try {
            const response = await fetch(url);
            const data = await response.json();
            data.forEach(item => {
                const opt = document.createElement("option");
                opt.value = item[valueAttr];
                opt.textContent = item[labelAttr];
                select.appendChild(opt);
            });
        } catch (err) {
            console.error(`Error loading ${selectId}:`, err);
        }
    }

    form.addEventListener("submit", function (e) {
        e.preventDefault();
        // Submit user logic here (build payload based on selected type, new/existing, and form fields)
        alert("User submitted successfully.");
        bootstrap.Modal.getInstance(document.getElementById("userModal")).hide();
        form.reset();
        // Reset modal to step 1
        step3.classList.add("d-none");
        step2.classList.add("d-none");
        step1.classList.remove("d-none");
        prevBtn.classList.add("d-none");
        submitBtn.classList.add("d-none");
        nextBtn.classList.remove("d-none");
        currentStep = 1;
    });
});
/*
document.addEventListener("DOMContentLoaded", () => {
    loadUsers();

    document.body.addEventListener('click', async (e) => {
        const target = e.target.closest("[data-action]");
        if (!target) return;

        const userId = target.dataset.id;
        const action = target.dataset.action;

        if (action === 'toggle') {
            await toggleUserStatus(userId);
        } else if (action === 'edit') {
            window.location.href = `/users/edit/${userId}`;
        } else if (action === 'delete') {
            if (confirm("Are you sure you want to delete this user?")) {
                await deleteUser(userId);
            }
        }
    });
});
*/
// Toggle new customer form
createNewCustomer.addEventListener('change', function () {
    document.getElementById('newCustomerForm').classList.toggle('d-none', !this.checked);
    customerSelect.disabled = this.checked;
});

// Toggle new employee form
createNewEmployee.addEventListener('change', function () {
    document.getElementById('newEmployeeForm').classList.toggle('d-none', !this.checked);
    employeeSelect.disabled = this.checked;
});

async function loadUsers() {
    const container = document.getElementById("userListContainer");
    container.innerHTML = `<div class="text-muted p-2">Loading users...</div>`;

    try {
        const res = await fetch('/api/users');
        const users = await res.json();

        if (!Array.isArray(users) || users.length === 0) {
            container.innerHTML = `<div class="text-muted p-2">No users found.</div>`;
            return;
        }

        container.innerHTML = "";

        users.forEach(user => {
            const item = document.createElement('div');
            item.className = `list-group-item d-flex justify-content-between align-items-center`;

            item.innerHTML = `
                <div>
                    <div class="fw-bold">${user.username}</div>
                    <div class="text-muted small">${user.email || '-'} | ${user.role?.name || '-'}</div>
                </div>
                <div class="btn-group btn-group-sm" role="group">
                    <button class="btn ${user.active ? 'btn-success' : 'btn-secondary'}"
                            data-action="toggle" data-id="${user.id}"
                            title="${user.active ? 'Deactivate' : 'Activate'}">
                        <i class="fa-solid fa-toggle-${user.active ? 'on' : 'off'}"></i>
                    </button>
                    <button class="btn btn-outline-warning" data-action="edit" data-id="${user.id}" title="Edit">
                        <i class="fa-solid fa-pen-to-square"></i>
                    </button>
                    <button class="btn btn-outline-danger" data-action="delete" data-id="${user.id}" title="Delete">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </div>
            `;

            container.appendChild(item);
        });
    } catch (error) {
        console.error("Error loading users:", error);
        container.innerHTML = `<div class="text-danger p-2">Error loading users.</div>`;
    }
}

async function toggleUserStatus(userId) {
    try {
        const res = await fetch(`/api/users/toggle/${userId}`, { method: 'PATCH' });

        if (!res.ok) {
            const error = await res.json();
            alert(`Failed to toggle: ${error.message || 'Unknown error'}`);
            return;
        }

        loadUsers(); // Refresh list
    } catch (err) {
        console.error("Toggle failed:", err);
        alert("Failed to toggle user status.");
    }
}

async function deleteUser(userId) {
    try {
        const res = await fetch(`/api/users/${userId}`, { method: 'DELETE' });

        if (!res.ok) {
            const error = await res.json();
            alert(`Delete failed: ${error.message || 'Unknown error'}`);
            return;
        }

        loadUsers(); // Refresh list
    } catch (err) {
        console.error("Delete failed:", err);
        alert("Failed to delete user.");
    }
}



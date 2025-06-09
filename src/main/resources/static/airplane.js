document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.getElementById("airplaneTableBody");

    fetch("/api/airplanes")
        .then(res => res.json())
        .then(data => {
            if (!Array.isArray(data) || data.length === 0) {
                tableBody.innerHTML = `
                    <tr>
                        <td colspan="9" class="text-center text-muted">No airplanes found.</td>
                    </tr>`;
                return;
            }

            tableBody.innerHTML = ""; // Clear existing

            data.forEach(plane => {
                const item = document.createElement('div');
                item.className = 'detail-item d-flex justify-content-between align-items-start py-3';

                item.innerHTML = `
                    <div class="flex-grow-1">
                        <div class="fw-bold fs-5">${plane.name || '-'}</div>
                        <div class="text-muted small">
                            ${plane.airlineName || '-'} &nbsp;|&nbsp; ${plane.country?.name || '-'}
                        </div>
                    </div>
                    <div class="flex-grow-1">
                        <span class="flex-grow-1"><strong>Model:</strong> ${plane.model?.name || '-'}</span>
                        <span class="flex-grow-1"><strong>Rows:</strong> ${plane.rowCount || '-'}</span>
                        <span class="flex-grow-1"><strong>Reg#:</strong> ${plane.registrationNumber || '-'}</span>
                    </div>
                    <div class="btn-group btn-group-sm" role="group">
                        <a href="/flights/from-airplane/${plane.id}" class="btn btn-outline-info" title="Flights">
                            <i class="fa-solid fa-plane-departure"></i>
                        </a>
                        <a href="/airplanes/view/${plane.id}" class="btn btn-outline-primary" title="View">
                            <i class="fa-solid fa-eye"></i>
                        </a>
                        <a href="/airplanes/edit/${plane.id}" class="btn btn-outline-warning" title="Edit">
                            <i class="fa-solid fa-pen-to-square"></i>
                        </a>
                        <a href="/airplanes/delete/${plane.id}" class="btn btn-outline-danger" title="Delete">
                            <i class="fa-solid fa-trash"></i>
                        </a>
                    </div>
                `;

                listContainer.appendChild(item);
            });
        })
        .catch(error => {
            console.error("Failed to fetch airplanes:", error);
            tableBody.innerHTML = `
                <tr>
                    <td colspan="9" class="text-danger text-center">Error loading airplanes.</td>
                </tr>`;
        });

    document.getElementById('airplaneForm').addEventListener('submit', saveAirplane);

    const flightModal = document.getElementById('airplaneModal');
    flightModal.addEventListener('shown.bs.modal', () => {
      refreshModelFunction();
    });

});

function refreshModelFunction(){
    populateSelect(
      '/api/countries',   // API URL
      'country',          // <select id="country">
      'name',             // Label to show (e.g. item.name)
      'id',               // Value to use (e.g. item.id)
      '-- Select Country --',
      0                   // Pre-select item with id = 3 (optional)
    );
    populateSelect(
      '/api/airplane-models',   // API URL
      'modelSelect',          // <select id="country">
      'name',             // Label to show (e.g. item.name)
      'id',               // Value to use (e.g. item.id)
      '-- Select Country --',
      0                   // Pre-select item with id = 3 (optional)
    );

}

async function saveAirplane(event) {
    event.preventDefault(); // Prevent default form submission

    // Collect form inputs
    const form = document.getElementById('airplaneForm');

    const airplane = {
        name: form.name.value.trim(),
        manufacture: form.manufacture.value.trim(),
        airline: form.airline.value.trim(),
        rowCount: parseInt(form.rowCount.value, 10),
        model: {},
        country: {}
    };

    // Handle model: use either selected ID or custom input
    const modelId = form.modelId.value;
    const customModel = form.customModel.value.trim();
    if (customModel) {
        airplane.model.name = customModel;
    } else if (modelId) {
        airplane.model.id = parseInt(modelId, 10);
    }

    // Country
    const countryId = form.countryId.value;
    if (countryId) {
        airplane.country.id = parseInt(countryId, 10);
    }

    try {
        const response = await fetch('/api/airplanes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(airplane)
        });

        if (!response.ok) {
            const err = await response.json();
            alert(`Failed: ${err.message || 'Unknown error'}`);
            return;
        }

        const result = await response.json();
        alert(result.message || 'Airplane saved successfully.');

        // Optionally reset the form or refresh the table
        form.reset();
        bootstrap.Modal.getInstance(document.getElementById('airplaneModal'))?.hide();
        if (typeof loadAirplanes === 'function') loadAirplanes(); // Optional refresh

    } catch (error) {
        console.error('Error saving airplane:', error);
        alert('Something went wrong. Please try again.');
    }
}


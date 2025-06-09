/**
 * Populates a <select> element with options from API response.
 *
 * @param {string} apiUrl - The API endpoint to fetch the list from.
 * @param {string} selectId - The ID of the <select> element to populate.
 * @param {string} labelAttr - The property in each object to show as the option label.
 * @param {string} valueAttr - The property to use as the option's value.
 * @param {string} placeholder - Placeholder option text (optional).
 * @param {string|number|null} selectedValue - Value to pre-select (optional).
 */
async function populateSelect(apiUrl, selectId, labelAttr, valueAttr, placeholder = "-- Select --", selectedValue = null) {
    const select = document.getElementById(selectId);
    if (!select) return console.error(`Select element #${selectId} not found`);

    try {
        const response = await fetch(apiUrl);
        const data = await response.json();

        // Clear existing options
        select.innerHTML = '';

        // Add placeholder
        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = placeholder;
        placeholderOption.disabled = true;
        placeholderOption.selected = !selectedValue;
        select.appendChild(placeholderOption);

        // Add fetched options
        data.data.forEach(item => {
            const option = document.createElement('option');
            option.value = item[valueAttr];
            option.textContent = item[labelAttr];

            if (selectedValue != null && item[valueAttr] == selectedValue) {
                option.selected = true;
            }

            select.appendChild(option);
        });
    } catch (err) {
        console.error('Error populating select:', err);
    }
}


function toggleMenu() {
    const sidebar = document.getElementById("mySelect");
    sidebar.classList.toggle("collapsed");
}
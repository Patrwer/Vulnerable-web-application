function fetchUsers() {
    const username = document.getElementById("username").value;
    const errorMessage = document.getElementById("error-message");
    const usersTable = document.getElementById("users-table");
    const tableBody = usersTable.querySelector("tbody");

    if (!username) {
        errorMessage.textContent = "Proszę podać nazwę użytkownika.";
        return;
    }

    errorMessage.textContent = "";
    tableBody.innerHTML = ""; // Wyczyść tabelę przed nowym wyszukiwaniem
    usersTable.style.display = "none"; // Ukryj tabelę przed załadowaniem danych

    fetch(`http://localhost:8080/users?username=${username}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Błąd sieciowy");
            }
            return response.json();
        })
        .then(data => {
            if (data.length === 0) {
                errorMessage.textContent = "Brak użytkowników do wyświetlenia.";
                return;
            }
            data.forEach(user => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                `;
                tableBody.appendChild(row);
            });
            usersTable.style.display = "block";
        })
        .catch(error => {
            errorMessage.textContent = "Nie udało się pobrać użytkowników.";
        });
}

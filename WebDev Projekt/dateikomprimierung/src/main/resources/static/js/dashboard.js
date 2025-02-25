function handlePresetChange() {
    const selectedOption = document.getElementById('preset-dropdown').value;
    
    // Alle Buttons zurücksetzen
    resetButtons();

    if (selectedOption === "default") {
        // "Preset auswählen" Button anzeigen
        document.getElementById('presetSelectButton').style.display = 'inline-block';
    } else if (selectedOption === "custom") {
        // Preset benennen
        document.getElementById('custom-preset').style.display = 'inline-block';
        // "Preset speichern" Button anzeigen
        document.getElementById('savePresetButton').style.display = 'inline-block';
        document.getElementById('generateLinkButton').style.display = 'inline-block';
    } else if (selectedOption === "preset1" || selectedOption === "preset2" || selectedOption === "preset3") {
        // "Link generieren" Button anzeigen
        document.getElementById('generateLinkButton').style.display = 'inline-block';
    } 
}

function resetButtons() {
    // Alle Buttons ausblenden
    document.getElementById('presetSelectButton').style.display = 'none';
    document.getElementById('savePresetButton').style.display = 'none';
    document.getElementById('generateLinkButton').style.display = 'none';
    document.getElementById('link').innerHTML = ''; // Linkanzeige zurücksetzen
}

function generateLink() {
    if (!userId) {
        alert("Fehler: Benutzer-ID wurde nicht gesetzt.");
        return;
    }

    fetch("http://localhost:8080/users/" + userId) // Benutzerdaten abrufen
    .then(response => response.json())
    .then(data => {
        if (data.zugriffslink) {
            const link = "http://localhost:8080" + data.zugriffslink;
            const linkInput = document.getElementById('generateLink');
            linkInput.value = link;
            linkInput.style.display = 'block';
        
        // Link anzeigen
    alert("Der Upload-Link: " + link);
        } else { 
            alert("Fehler: Kein Zugriffslink gefunden.");
        }
    })
    .catch(error => {
        alert("Fehler beim Generieren des Links: " + error.message);
    });
}

// Link kopieren
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("copyLink").addEventListener("click", function () {
        const linkInput = document.getElementById("generateLink");
        if (linkInput && linkInput.value) {
            linkInput.select();
            navigator.clipboard.writeText(linkInput.value).then(() => {
                alert("Link wurde kopiert!");
            }, () => {
                console.error("Fehler beim Kopieren");
            });
        }
    });
});
document.getElementById("userForm").addEventListener("submit", function (event) {
    event.preventDefault();  // Verhindert das normale Abschicken des Formulars
    createUser();
});

let userId = null; // Globaler Scope für die Benutzer-ID

function createUser() {
    const userData = {
        vorname: document.getElementById("vorname").value,
        nachname: document.getElementById("nachname").value,
        komprimierung: document.getElementById("komprimierung").value
    };
    
    fetch("http://localhost:8080/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userData)
    })
    .then(response => {
        if (!response.ok) {
            // Falls der Server Fehler zurückgibt, wird eine Fehlermeldung ausgegeben
            return response.json().then(err => {
                throw new Error(`Fehler: ${response.status} - ${err.message || 'Unbekannter Fehler'}`);
            });
        }
        return response.json(); // Erfolgreiche Antwort wird hier zurückgegeben
    })
    .then(data => {
        if (data.id) {
            // Wenn die ID erfolgreich zurückgegeben wird
            alert("User wurde erstellt! ID: " + data.id);
            userId = data.id; // Die Benutzer-ID wird gespeichert
            const uploadLink = "http://localhost:8080" + data.zugriffslink;
            alert("Dein Upload-Link: " + uploadLink);
        } else {
            alert("Fehler beim Erstellen des Users, keine ID erhalten.");
        }
    })
    .catch(error => {
        alert("Fehler beim Erstellen des Users: " + error.message);
    });
}

    function enableUploadImageButton() {
        const uploadButton = document.getElementById('uploadImageButton');
        if (uploadButton) {
          uploadButton.disabled = false; // oder eine andere Logik, um den Button zu aktivieren
        }
      }
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
    document.getElementById('linkDisplay').innerHTML = ''; // Linkanzeige zurücksetzen
}

function showPresetSelect() {
    alert("Preset-Auswahl wurde angezeigt");
    // Hier könnte eine Logik zum Anzeigen von verfügbaren Presets implementiert werden.
}

function savePreset() {
    alert("Individuelles Preset wurde gespeichert.");
    // Hier könnte eine Logik zum Speichern des individuellen Presets implementiert werden.
}

function generateLink() {
    const PresetSelect = document.getElementById('preset-dropdown');
    const selectedPreset = PresetSelect.value;
        
    let presetName = selectedPreset;

    // Falls "Individuelles Preset" gewählt wurde, nutze den benutzerdefinierten Namen
    if (selectedPreset === 'custom') {
        presetName = document.getElementById('custom-preset').value.trim();
        if (!presetName) {
            alert('Bitte gib einen Namen für das Preset ein.');
            return;
            }
        }

    // Eindeutige Benutzer-ID generieren
    const userId = Math.floor(Math.random() * 1000000);

    // Personalisierten Link mit Benutzer-ID erstellen
    const link = `http://127.0.0.1:5500/WebDev%20Projekt/dateikomprimierung/Frontend/index.html?user=${userId}&preset=${encodeURIComponent(presetName)}`;

    // Link anzeigen
    document.getElementById('generateLink').innerHTML = `<a href="${link}" target="_blank">${link}</a>`;

    // Link in Eingabefeld einfügen
    const linkInput = document.getElementById('generateLink');
    linkInput.value = link;
    linkInput.style.display = 'block';
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
document.addEventListener("DOMContentLoaded", function () {
    const imageUpload = document.getElementById("imageUpload");
    const dropZone = document.getElementById("drop-zone");
    const fileList = document.getElementById("file-list");
    const uploadProgressBar = document.getElementById("upload-progress-bar");
    const uploadProgressText = document.getElementById("upload-progress-text");
    const compressProgressBar = document.getElementById("compress-progress-bar");
    const compressProgressText = document.getElementById("compress-progress-text");
    const compressedFiles = document.getElementById("compressedFiles");
    const uploadImageButton = document.getElementById("uploadImageButton");
    const compressButton = document.getElementById("compress-selected");
    const downloadAllButton = document.getElementById("downloadAllButton");
    const downloadSelectedButton = document.getElementById("downloadSelectedButton");

    let files = []; // Zwischenspeicher für die ausgewählten Dateien

    // Dateien bei Auswahl hinzufügen
    imageUpload.addEventListener("change", function (event) {
        const selectedFiles = event.target.files;
        if (selectedFiles.length > 0) {
            files = Array.from(selectedFiles);
            displayFiles(files);
        }
    });

    // Upload-Button Event
    uploadImageButton.addEventListener("click", function () {
        if (files.length === 0) {
            alert("Bitte wählen Sie zuerst Dateien aus.");
            return;
        }
        
        files.forEach(file => {
            uploadImage(file);
        });
    });

    // Drag & Drop Events
    dropZone.addEventListener("dragover", (event) => {
        event.preventDefault();
        dropZone.style.borderColor = "#4CAF50";
    });

    dropZone.addEventListener("dragleave", () => {
        dropZone.style.borderColor = "#ddd";
    });

    dropZone.addEventListener("drop", (event) => {
        event.preventDefault();
        dropZone.style.borderColor = "#ddd";
        files = Array.from(event.dataTransfer.files);
        displayFiles(files);
    });

    function displayFiles(selectedFiles) {
        // Stelle sicher, dass die Liste nicht erneut geleert wird, wenn du bereits die gleichen Dateien hast
        selectedFiles.forEach(file => {
            // Überprüfe, ob der Dateiname bereits in der Liste enthalten ist
            if (!Array.from(fileList.children).some(item => item.querySelector("span").textContent === file.name)) {
                displayFile(file); // Nur neue Dateien anzeigen
            }
        });
    }

    function displayFile(file) {
        const listItem = document.createElement("li");

        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
    
        const fileName = document.createElement("span");
        fileName.textContent = file.name;
    
        listItem.appendChild(checkbox);
        listItem.appendChild(fileName);
        fileList.appendChild(listItem);
    }

    // Bilder komprimieren
    compressButton.addEventListener("click", function () {
        let selectedFiles = Array.from(fileList.children)
            .filter(item => item.querySelector("input").checked)  // Nur ausgewählte Dateien
            .map(item => item.querySelector("span").textContent); // Dateinamen extrahieren

        if (selectedFiles.length === 0) {
            alert("Bitte wählen Sie mindestens eine Datei aus.");
            return;
        }

        startCompression(selectedFiles);
    });

    function displayCompressedFiles(compressedFileDetails) {
        // Überprüfe, ob die komprimierte Datei bereits in der Liste ist
        compressedFileDetails.forEach(fileDetails => {
            // Überprüfe, ob die Datei bereits existiert
            if (!Array.from(compressedFiles.children).some(item => item.querySelector("span").textContent === fileDetails.fileName)) {
                const listItem = document.createElement("li"); // Ein neues li für jede komprimierte Datei
    
                // Den Dateinamen hinzufügen
                const fileNameSpan = document.createElement("span");
                fileNameSpan.textContent = fileDetails.fileName;
    
                // Checkbox für jede komprimierte Datei hinzufügen
                const checkbox = document.createElement("input");
                checkbox.type = "checkbox";
                checkbox.setAttribute("data-filename", fileDetails.fileName); // Setze den Dateinamen als Attribut für die Checkbox
    
                // Download-Button hinzufügen
                const downloadButton = document.createElement("button");
                downloadButton.textContent = "Download";
                downloadButton.setAttribute("data-filename", fileDetails.fileName);
                downloadButton.onclick = function () {
                    window.location.href = fileDetails.zugriffslink; // Führt den Download aus
                };

                // Hinzufügen der Elemente zum Listeneintrag
                listItem.appendChild(checkbox);
                listItem.appendChild(fileNameSpan);
                listItem.appendChild(downloadButton);  // Den Download-Button hinzufügen
    
                // Das li zur ul-Liste hinzufügen
                compressedFiles.appendChild(listItem);
            }
        });
    }
    
// Download für alle komprimierten Dateien
downloadAllButton.addEventListener("click", function () {
    const allFiles = Array.from(compressedFiles.children);
    if (allFiles.length === 0) {
        alert("Keine komprimierten Dateien zum Herunterladen.");
        return;
    }
    allFiles.forEach(item => {
        const downloadButton = item.querySelector("button");
        if (downloadButton) {
            downloadButton.click(); // Führt den Download durch
        }
    });
});

// Download für ausgewählte Dateien
downloadSelectedButton.addEventListener("click", function () {
    const selectedFiles = Array.from(compressedFiles.children)
        .filter(item => item.querySelector("input").checked)  // Nur ausgewählte Dateien
        .map(item => {
            return {
                fileName: item.querySelector("span").textContent,
                zugriffslink: item.querySelector("button").getAttribute("data-filename")  // Holen des Links aus dem Button
            };
        });

    if (selectedFiles.length === 0) {
        alert("Bitte wählen Sie mindestens eine Datei aus.");
        return;
    }

    selectedFiles.forEach(file => {
        window.location.href = file.zugriffslink; // Führt den Download für jede ausgewählte Datei aus
    });
});

    function startCompression(selectedFiles) {
        let progress = 0;
        compressProgressBar.style.width = "0%";
        compressProgressText.textContent = "0%";

        selectedFiles.forEach(fileName => {
            const file = files.find(f => f.name === fileName);  // Datei aus dem 'files' Array holen
            if (!file) {
                console.error(`Datei ${fileName} nicht gefunden.`);
                return;
            }

            // Holen der userId aus der URL
            const urlParams = new URLSearchParams(window.location.search);
            const userId = urlParams.get("userId");

            if (userId) {
                console.log("Extrahierte userId aus der URL:", userId);  // Logge die userId zur Überprüfung

                const formData = new FormData();
                formData.append("image", file);  // Datei hinzufügen
                formData.append("userId", userId);  // userId hinzufügen

                fetch(`http://localhost:8080/api/image-compression/compress?userId=${userId}`, {
                    method: "POST",
                    body: formData
                })
                .then(response => {
                    console.log("Antwort des Servers: ", response);  // Loggt die Antwort
                    return response.text();  // Gibt die JSON-Daten zurück
                })
                .then(text => {
                    try {
                        const data = JSON.parse(text);  // Versuche, den Text in JSON zu parsen
                        console.log("Erhaltene JSON-Daten:", data);
                        if (data.success) {
                            const zugriffslink = data.zugriffslink; // Der Download-Link, den das Backend zurückgibt
                            console.log("Download-Link:", zugriffslink);
                            displayCompressedFiles([{fileName: fileName, zugriffslink: zugriffslink}]); // Hier wird das Display der komprimierten Dateien aufgerufen
                        } else {
                            console.error("Fehlerdetails vom Backend:", data);
                        }
                    } catch (error) {
                        console.error("Antwort konnte nicht als JSON geparst werden:", error);
                        alert("❌ Fehler: Die Antwort vom Server war nicht im richtigen Format.");
                    }
                })
                .catch(error => {
                    console.error("Fehler bei der Komprimierung:", error);
                    alert("❌ Fehler: Die Komprimierung der Dateien ist fehlgeschlagen.");
                });
            } else {
                console.error("Kein userId-Parameter gefunden.");
            }
        });
    }

    function uploadImage(file) {
        const formData = new FormData();
        formData.append("image", file);

        fetch("http://localhost:8080/api/image-compression/upload", {
            method: "POST",
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const zugriffslink = data.downloadLink || data.zugriffslink;                // Optional: Weitere Logik für das Verarbeiten des Links
            }
        });
    }
});

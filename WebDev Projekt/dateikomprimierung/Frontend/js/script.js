document.addEventListener("DOMContentLoaded", function () {
    const fileInput = document.getElementById("file-input");
    const fileSelect = document.getElementById("file-select");
    const dropZone = document.getElementById("drop-zone");
    const fileList = document.getElementById("file-list");
    const uploadProgressBar = document.getElementById("upload-progress-bar");
    const uploadProgressText = document.getElementById("upload-progress-text");
    const compressProgressBar = document.getElementById("compress-progress-bar");
    const compressProgressText = document.getElementById("compress-progress-text");
    const compressedFilesList = document.getElementById("compressed-files");
    const compressButton = document.getElementById("compress-selected");
    const deleteSelectedButton = document.createElement("button");

    let files = [];
    let compressedFiles = new Set(); 

    // "Alle markierten löschen"-Button hinzufügen (zuerst versteckt)
    deleteSelectedButton.textContent = "Alle markierten löschen";
    deleteSelectedButton.id = "delete-selected";
    deleteSelectedButton.style.display = "none";
    deleteSelectedButton.addEventListener("click", deleteSelectedFiles);
    fileList.parentNode.appendChild(deleteSelectedButton);    

    fileSelect.addEventListener("click", () => fileInput.click());
    fileInput.addEventListener("change", (event) => handleFiles(event.target.files));

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
        handleFiles(event.dataTransfer.files);
    });

    function handleFiles(selectedFiles) {
        let progress = 0;
        uploadProgressBar.style.width = "0%";
        uploadProgressText.textContent = "0%";

        const interval = setInterval(() => {
            if (progress >= 100) {
                clearInterval(interval);
                for (let file of selectedFiles) {
                    if (!files.some(f => f.name === file.name)) { 
                        files.push(file);
                        displayFile(file);
                    }
                }
            } else {
                progress += 10;
                uploadProgressBar.style.width = progress + "%";
                uploadProgressText.textContent = progress + "%";
            }
        }, 300);
    }

    function displayFile(file) {
        const listItem = document.createElement("li");
        
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.addEventListener("change", updateDeleteButtonVisibility); // Button bei Auswahl anzeigen/verstecken

        const fileName = document.createElement("span");
        fileName.textContent = file.name;

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "❌";
        deleteButton.classList.add("delete-btn");
        deleteButton.addEventListener("click", function () {
            deleteFile(file.name);
        });

        listItem.appendChild(checkbox);
        listItem.appendChild(fileName);
        listItem.appendChild(deleteButton);
        fileList.appendChild(listItem);
    }

    function deleteFile(fileName) {
        files = files.filter(file => file.name !== fileName);
        compressedFiles.delete(fileName);

        Array.from(fileList.children).forEach(item => {
            if (item.querySelector("span").textContent === fileName) {
                fileList.removeChild(item);
            }
        });

        updateDeleteButtonVisibility();
    }

    function deleteSelectedFiles() {
        const selectedFiles = Array.from(fileList.children).filter(item => 
            item.querySelector("input").checked
        );

        selectedFiles.forEach(item => {
            const fileName = item.querySelector("span").textContent;
            deleteFile(fileName);
        });

        updateDeleteButtonVisibility();
    }

    function updateDeleteButtonVisibility() {
        const anySelected = Array.from(fileList.children).some(item => 
            item.querySelector("input").checked
        );
        deleteSelectedButton.style.display = anySelected ? "block" : "none";
    }

    compressButton.addEventListener("click", function () {
        let selectedFiles = Array.from(fileList.children)
            .filter(item => item.querySelector("input").checked)
            .map(item => item.querySelector("span").textContent)
            .filter(fileName => !compressedFiles.has(fileName));

        if (selectedFiles.length === 0) return;

        startCompression(selectedFiles);
    });

    function startCompression(selectedFiles) {
        let progress = 0;
        compressProgressBar.style.width = "0%";
        compressProgressText.textContent = "0%";

        const interval = setInterval(() => {
            if (progress >= 100) {
                clearInterval(interval);
                selectedFiles.forEach(file => {
                    if (!compressedFiles.has(file)) { 
                        compressedFiles.add(file);
                        let listItem = document.createElement("li");
                        listItem.textContent = file;
                        compressedFilesList.appendChild(listItem);
                    }
                });
            } else {
                progress += 10;
                compressProgressBar.style.width = progress + "%";
                compressProgressText.textContent = progress + "%";
            }
        }, 400);
    }
});

function applyPresetFromUrl() {
    const params = new URLSearchParams(window.location.search);
    const preset = params.get("preset");
    const user = params.get("user");

    if (preset) {
        console.log(`Benutzer ${user} nutzt Preset: ${preset}`);
    }

    const presetDropdown = document.getElementById("preset-dropdown");
    if (presetDropdown) {
        for (let option of presetDropdown.options) {
            if (option.value === preset) {
                option.selected = true;
                }    
            }
        }

    // Hier könnte die Logik zum Anwenden des Presets implementiert werden.
    window.onload = applyPresetFromUrl;
}


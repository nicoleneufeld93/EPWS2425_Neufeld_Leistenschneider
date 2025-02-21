package com.webdev.dateikomprimierung.controller;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webdev.dateikomprimierung.model.User;
import com.webdev.dateikomprimierung.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST-Anfrage zum Erstellen eines Nutzers und Ausgabe des Zugriffslinks
    @PostMapping
    public User createUser(@RequestBody User user) {
        // Benutzer wird erstellt
        User createdUser = userService.createUser(user);
        
        // Generiere den Zugriffslink für den neu erstellten Nutzer
        String zugriffslink = "http://localhost:8080/api/image-compression/upload?userId=" + createdUser.getId();
        
        // Setze den Zugriffslink des Nutzers
        createdUser.setZugriffslink(zugriffslink);
        
        // Rückgabe des Users mit dem Zugriffslink
        return createdUser; // Spring wird das User-Objekt automatisch in JSON umwandeln
    }

    // GET-Anfrage zum Abrufen eines Nutzers nach ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        // Abrufen des Nutzers anhand der ID
        User user = userService.getUserById(id);
        
        if (user != null) {
            // Wenn der Nutzer existiert, gebe ihn als JSON zurück (HTTP Status 200)
            return ResponseEntity.ok(user); // Spring wandelt das User-Objekt in JSON um
        } else {
            // Wenn der Nutzer nicht gefunden wurde, gebe eine Fehlermeldung mit HTTP Status 404 zurück
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{ \"success\": false, \"error\": \"User not found.\" }"); // JSON-Fehlermeldung
        }
    }

    // GET-Anfrage zum Herunterladen eines komprimierten Bildes
    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename) {
        try {
            // Der Pfad zur komprimierten Datei wird erstellt
            Path filePath = Paths.get("path/to/compressed/images").resolve(filename);
            
            // Erstelle ein Resource-Objekt mit der Datei
            Resource resource = new UrlResource(filePath.toUri());

            // Überprüfe, ob die Datei existiert oder lesbar ist
            if (resource.exists() || resource.isReadable()) {
                // Bestimme den Content-Type der Datei basierend auf dem Dateinamen
                String contentType = URLConnection.guessContentTypeFromName(filename);
                if (contentType == null) {
                    contentType = "application/octet-stream"; // Fallback für unbekannte Content-Typen
                }

                // Gib die Datei als Download zurück mit dem richtigen Content-Type und Header
                return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.parseMediaType(contentType)) // Content-Type setzen
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource); // Datei wird als Antwort zurückgegeben
            } else {
                // Wenn die Datei nicht gefunden oder nicht lesbar ist, gebe eine Fehlermeldung zurück (HTTP Status 404)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{ \"success\": false, \"error\": \"File not found.\" }"); // JSON-Fehlermeldung
            }
        } catch (IOException e) {
            // Wenn ein Fehler beim Zugriff auf die Datei auftritt, gebe eine Fehlermeldung zurück (HTTP Status 500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{ \"success\": false, \"error\": \"Error accessing file.\" }"); // JSON-Fehlermeldung
        }
    }
}

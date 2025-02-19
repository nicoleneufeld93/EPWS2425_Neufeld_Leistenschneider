package com.webdev.dateikomprimierung.controller;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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
        User createdUser = userService.createUser(user); // Benutzer erstellen
        String zugriffslink = "/api/image-compression/upload?userId=" + createdUser.getId();
        createdUser.setZugriffslink(zugriffslink); // Zugriffslink wird direkt gesetzt
        return createdUser; // Der Benutzer mit dem Zugriffslink wird zurückgegeben
    }

    // GET-Anfrage zum Abrufen eines Nutzers nach ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id); // Benutzer anhand der ID abrufen
    }

    // GET-Anfrage zum Herunterladen eines komprimierten Bildes
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
        // Passe den Pfad zu deinem Ordner an, in dem die komprimierten Bilder gespeichert sind
        Path filePath = Paths.get("path/to/compressed/images").resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            // Dynamischer Content-Type basierend auf der Dateiendung
            String contentType = URLConnection.guessContentTypeFromName(filename);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Fallback, falls der MIME-Typ nicht ermittelt werden kann
            }

            return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType)) // Dynamischer Content-Type
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
        } else {
            throw new IOException("Die Datei konnte nicht gefunden werden.");
        }
    }
}

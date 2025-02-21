package com.webdev.dateikomprimierung;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webdev.dateikomprimierung.model.User;
import com.webdev.dateikomprimierung.service.UserService;

@RestController
@RequestMapping("/api/image-compression")
public class Komprimierer {

    // Weiterleitung zur Index-Seite
    @GetMapping("/upload")
    public ResponseEntity<Void> redirectToIndex(@RequestParam("userId") Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/index.html?userId=" + userId);  // Füge userId zur Weiterleitung hinzu
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // Rückgabe einer Weiterleitung (HTTP 302)
    }

    // Temporärer Pfad für komprimierte Bilder
    Path relativePath = Paths.get("compressed-images\\").normalize();
    private final String OUTPUT_DIRECTORY = relativePath.toAbsolutePath().toString();

    @Autowired
    private UserService userService; // UserService für den Zugriff auf User-Daten

    // Komprimierung des Bildes
    @CrossOrigin(origins = "http://localhost:3000")  // Frontend-Domain

    @PostMapping("/compress")
    public ResponseEntity<String> compressImage(@RequestParam("image") MultipartFile image,
                                                @RequestParam("userId") Long userId) {
        System.out.println("User ID: " + userId);
        System.out.println("Image name: " + image.getOriginalFilename());

        try {
            // Überprüfen, ob das Verzeichnis existiert, andernfalls erstellen
            File directory = new File(OUTPUT_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = image.getOriginalFilename();
            if (originalFilename == null) {
                // Fehlerbehandlung für ungültige Datei
                return ResponseEntity.badRequest().body("{ \"success\": false, \"message\": \"Invalid file. Please upload a valid image.\" }");
            }

            // Überprüfen des Content-Types der Datei
            String contentType = image.getContentType();
            List<String> allowedImageTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg", "image/gif", "image/bmp", "image/webp");
            if (!allowedImageTypes.contains(contentType)) {
                // Fehlerbehandlung für nicht unterstützte Bildformate
                return ResponseEntity.badRequest().body("{ \"success\": false, \"message\": \"Die Datei ist kein unterstütztes Bildformat.\" }");
            }

            // Hole die Komprimierungsrate des Nutzers aus der DB
            User user = userService.getUserById(userId);
            if (user == null) {
                // Fehlerbehandlung, falls der User nicht existiert
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"success\": false, \"message\": \"User mit ID " + userId + " nicht gefunden\" }");
            }

            int komprimierungsrate = user.getKomprimierung();  // Hole die Komprimierungsrate des Nutzers

            // Speichern des Bildes und Komprimierung mit FFmpeg
            File inputFile = new File(OUTPUT_DIRECTORY, originalFilename);
            image.transferTo(inputFile);
            String outputFileName = "compressed-" + originalFilename;
            File outputFile = new File(OUTPUT_DIRECTORY, outputFileName);

            // FFmpeg Komprimierung
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "ffmpeg",
                    "-i", inputFile.getAbsolutePath(),
                    "-qscale:v", String.valueOf(komprimierungsrate),
                    outputFile.getAbsolutePath()
            );
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            // Lösche das ursprüngliche Bild nach der Komprimierung
            inputFile.delete();

            if (exitCode == 0) {
                // Generiere den Zugriffslink für den Benutzer
                String zugriffslink = "/api/image-compression/download/" + outputFileName;
                userService.setZugriffslink(user, zugriffslink); // Setze den Zugriffslink in der DB
                
                // Rückgabe einer erfolgreichen Antwort mit dem Zugriffslink als JSON
                return ResponseEntity.ok("{ \"success\": true, \"zugriffslink\": \"" + zugriffslink + "\" }");
            } else {
                // Fehlerbehandlung für FFmpeg-Fehler
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ \"success\": false, \"message\": \"Error during compression. FFmpeg exited with code: " + exitCode + "\" }");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Fehlerbehandlung für IO oder Unterbrechungsfehler
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ \"success\": false, \"message\": \"Error occurred during image compression: " + e.getMessage() + "\" }");
        }
    }

    // Endpunkt zum Herunterladen des komprimierten Bildes
    @GetMapping("/download/{filename}")
    public ResponseEntity<FileSystemResource> downloadCompressedImage(@PathVariable("filename") String filename) {
        try {
            File file = new File(OUTPUT_DIRECTORY, filename);
            if (!file.exists()) {
                // Fehlerbehandlung für nicht gefundene Datei
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            FileSystemResource resource = new FileSystemResource(file);
            // Rückgabe der Datei als Download-Link
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            // Fehlerbehandlung für unerwartete Fehler
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

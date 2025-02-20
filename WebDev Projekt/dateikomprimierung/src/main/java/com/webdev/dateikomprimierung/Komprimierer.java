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

@GetMapping("/upload")
public ResponseEntity<Void> redirectToIndex(@RequestParam("userId") Long userId) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/index.html?userId=" + userId);  // Füge userId zur Weiterleitung hinzu
    return new ResponseEntity<>(headers, HttpStatus.FOUND);
}

    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

    Path relativePath = Paths.get("compressed-images\\").normalize();
    private final String OUTPUT_DIRECTORY = relativePath.toAbsolutePath().toString();

    @Autowired
    private UserService userService; // UserService für den Zugriff auf User-Daten

    // Komprimierung des Bildes
    @CrossOrigin(origins = "http://localhost:3000")  // Frontend-Domain

    @PostMapping("/compress")
    public String compressImage(@RequestParam("image") MultipartFile image,
                                @RequestParam("userId") Long userId) {
    System.out.println("User ID: " + userId);
    System.out.println("Image name: " + image.getOriginalFilename());

        try {
            File directory = new File(OUTPUT_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = image.getOriginalFilename();
            if (originalFilename == null) {
                return "Invalid file. Please upload a valid image.";
            }

            String contentType = image.getContentType();
            List<String> allowedImageTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg", "image/gif", "image/bmp", "image/webp");
            if (!allowedImageTypes.contains(contentType)) {
                return "Die Datei ist kein unterstütztes Bildformat.";
            }

            // Hole die Komprimierungsrate des Nutzers aus der DB
            User user = userService.getUserById(userId);
            if (user == null) {
                return "{\"success\": false, \"message\": \"User mit ID " + userId + " nicht gefunden\"}";
                    }
            int komprimierungsrate = user != null ? user.getKomprimierung() : 50;  // Standardwert 50, falls kein User gefunden wird

            // Speicher das Bild temporär
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

            inputFile.delete();

            if (exitCode == 0) {
                // Generiere den Zugriffslink für den Benutzer
                String zugriffslink = "/api/image-compression/download/" + outputFileName;
                if (user != null) {
                    userService.setZugriffslink(user, zugriffslink); // Setze den Zugriffslink
                    return "{ \"success\": true, \"zugriffslink\": \"" + zugriffslink + "\" }";
                } else {
                    return "User not found.";
                }
            } else {
                return "Error during compression. FFmpeg exited with code: " + exitCode;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error occurred during image compression: " + e.getMessage();
        }
    }

    // Endpunkt zum Herunterladen des komprimierten Bildes
    @GetMapping("/download/{filename}")
    public ResponseEntity<FileSystemResource> downloadCompressedImage(@PathVariable("filename") String filename) {
        try {
            File file = new File(OUTPUT_DIRECTORY, filename);
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            FileSystemResource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

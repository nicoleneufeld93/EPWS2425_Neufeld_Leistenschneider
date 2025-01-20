package com.webdev.dateikomprimierung;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/image-compression")
public class Komprimierer {

        Path relativePath = Paths.get("compressed-images\\").normalize();
        private final String OUTPUT_DIRECTORY = relativePath.toAbsolutePath().toString();
        //"../../../../../../compressed-images\\"
    
    //Anfrage für Komprimierung, falls keine quality angegeben wird, liegt der standard Wert bei 75
        @PostMapping("/compress")
        public String compressImage(@RequestParam("image") MultipartFile image,
                                    @RequestParam(value = "quality", defaultValue = "50") int quality) {

            //Test, ob der Ordner besteht, sonst neu erstellen
            try {
                File directory = new File(OUTPUT_DIRECTORY);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                // Gibt es das Bild, oder nicht?
                String originalFilename = image.getOriginalFilename();
                if (originalFilename == null) {
                    return "Invalid file. Please upload a valid image.";
                }

                //Test, ob die Datei wirklich ein Bild ist mit verschiedenen Bildformaten
                String contentType = image.getContentType();
                List<String> allowedImageTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg", "image/gif", "image/bmp", "image/webp");
                if (!allowedImageTypes.contains(contentType)) {
                    return "Die Datei ist kein unterstütztes Bildformat.";
                }
                //Test, ob das Guthaben ausreicht. Sp�ter Abfrage des Guthabens aus der Datenbank
                int guthaben=0;
                System.out.println("Das Guthaben betr�gt:" + guthaben);
                if (guthaben <1){
                    return "Guthaben reicht nicht aus";
                }
    
                // Define input and output files
                File inputFile = new File(OUTPUT_DIRECTORY, originalFilename);
                image.transferTo(inputFile); // Bild temporär hier zwischenspeichern um es zu verarbeiten
                System.out.println("Output Directory: " + OUTPUT_DIRECTORY);
                //String outputFileName = "compressed-" + originalFilename;
                String outputFileName = "compressed-" + originalFilename;
                File outputFile = new File(OUTPUT_DIRECTORY, outputFileName);
    
                // FFMPeg Befehl
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "ffmpeg",
                        "-i", inputFile.getAbsolutePath(), // Input 
                        "-qscale:v", String.valueOf(quality), // Komprimierungsqualität, je niedriger die Zahl, desto weniger Komprimierung
                        outputFile.getAbsolutePath() // Output file
                );
    
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
    
                // Temporäre Datei wieder löschen
                inputFile.delete();
    
                //Fehler oder Erfolg ausgeben
                if (exitCode == 0) {
                    guthaben--;
                    System.out.println("Das Guthaben betr�gt:" + guthaben);
                    return "Image successfully compressed and saved to: " + outputFile.getAbsolutePath();
                } else {
                    return "Error during compression. FFmpeg exited with code: " + exitCode;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "Error occurred during image compression: " + e.getMessage();
            }
        }
    }




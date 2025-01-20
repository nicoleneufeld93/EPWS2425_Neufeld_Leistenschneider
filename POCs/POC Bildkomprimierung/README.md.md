## Dateikomprimierung von Bilddateien

**Beschreibung**
Das Ziel ist die Entwicklung einer Methode zur effizienten Komprimierung von Bilddateien. Die Bilder sollen so verkleinert werden, dass die Dateigröße reduziert wird, während die visuelle Qualität erhalten bleibt. Ein Prototyp wird erstellt, der gängige Bildformate (z. B. JPEG, PNG) verarbeitet und die Ergebnisse hinsichtlich Qualität und Dateigröße prüft.

**Exit-Kriterien**
* Die Dateigröße wird merklich reduziert.
* Die visuelle Qualität bleibt subjektiv unverändert (keine sichtbaren Artefakte).
* Die Komprimierung dauert für typische Bildgrößen nicht unverhältnismäßig lange.
* Die komprimierten Bilder sind in gängigen Browsern und Programmen kompatibel.

**Fail-Kriterien**
* Sichtbare Verluste in der Bildqualität treten auf.
* Die Komprimierung führt nicht zu einer wahrnehmbaren Reduktion der Dateigröße.
* Die Komprimierung ist zeitaufwändig oder führt zu Fehlern bei speziellen Formaten.

**Fallbacks**
* Formatwechsel: Einsatz alternativer Formate zur effizientere Komprimierung.
* Adaptive Algorithmen: Verwendung dynamischer Komprimierungsalgorithmen, die je nach Bildinhalt die Qualität anpassen.
* Externe Tools: Nutzung etablierter Lösungen für spezifische Optimierungen.

**Ausführung des POCs**
* Klonen und installieren des Repositorys
* Starten der Anwendung
* in einer Konsole den folgenden Befehl eingeben (curl muss installiert sein):curl -X POST -F "image=@**(hier Link zum Bild)**" -F "quality=75" http://localhost:8080/api/image-compression/compress 
* Im Ordner WebDEvProjekt/compressed-images befindet sich das komprimierte Bild
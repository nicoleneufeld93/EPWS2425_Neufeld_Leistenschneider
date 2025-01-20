## Dateikomprimierung von Videodateien


**Beschreibung**

Das Ziel ist die Entwicklung einer Methode zur effizienten Komprimierung von Videodateien. Die Videos sollen so verkleinert werden, dass sie weniger Speicherplatz beanspruchen und schneller hoch- oder heruntergeladen werden können, ohne dass die sichtbare Qualität merklich beeinträchtigt wird. Ein Prototyp wird erstellt, der Videodateien mit verschiedenen Codecs (z. B. H.264, H.265) und Bitraten verarbeitet. Die komprimierten Videos werden auf Qualität, Dateigröße und Kompatibilität getestet.

**Exit-Kriterien**
* Die Dateigröße wird signifikant reduziert.
* Die sichtbare Qualität des Videos bleibt für den Endnutzer subjektiv unverändert.
* Die Komprimierung dauert für typische Videogrößen nicht unverhältnismäßig lange.
* Die komprimierten Videos sind in gängigen Playern und Plattformen kompatibel.

**Fail-Kriterien**
* Die Videoqualität weist deutliche Verzerrungen auf.
* Die Komprimierung führt nicht zu einer großen Reduktion der Dateigröße.
* Die Komprimierung ist zeitaufwändig oder verursacht Fehler bei großen Dateien.

**Fallbacks:**
* Alternative Ansätze: Wenn die benutzten Algorithmen/Librarys nicht die gewünschten Ergebnisse liefern, können alternative Ansätze zur Lösung getestet werden.
* Umwandlung: Falls die Qualität bei starker Komprimierung leidet, kann eine Kombination aus Komprimierung und Änderung des Dateiformats getestet werden.
* externe Tools: Wenn die Entwicklung nicht die gewünschten Ergebnisse liefert, können für spezifische Fälle externe Tools zur Komprimierung verwendet werden.

**Ausführung des POCs**
* Die Audio- und Videokomprimierung wurde erst einmal in den Hintergrund gestellt, da das Augenmerk erst einmal auf der Bildkomprimierung liegt
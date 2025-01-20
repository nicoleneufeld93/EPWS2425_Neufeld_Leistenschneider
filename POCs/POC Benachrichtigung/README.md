## Benachrichtigung an Benutzer senden
**Beschreibung**

Das Ziel ist die Implementierung eines Systems, welches Benutzern nach Abschluss des Upload- und Komprimierungsprozesses automatisch eine Benachrichtigung sendet. Diese Benachrichtigung soll den Nutzer darüber informieren, dass die Dateien erfolgreich verarbeitet wurden und zum Download bereitstehen. Der Prototyp wird mit Testnutzern erprobt, um sicherzustellen, dass Benachrichtigungen korrekt und zeitnah ausgelöst werden.

**Exit-Kriterien**
* Nach jedem abgeschlossenen Upload und der Komprimierung erhält der Nutzer eine Benachrichtigung.
* Die Benachrichtigung enthält alle relevanten Informationen, z. B. den Status der Verarbeitung und den Link zum Download.
* Die Benachrichtigung wird über verschiedene Kanäle oder im System-Dashboard angezeigt.
* Die Benachrichtigung wird zuverlässig und ohne Verzögerungen ausgelöst.

**Fail-Kriterien**
* Die Benachrichtigung wird gar nicht oder mit erheblicher Verzögerung versendet.
* Die Benachrichtigung enthält fehlerhafte Informationen (z. B. falscher Status oder unvollständige Links).
* Die Benachrichtigungsfunktion belastet das System so stark, dass andere Prozesse beeinträchtigt werden.
* Mehrere Uploads eines Nutzers führen zu doppelten oder fehlenden Benachrichtigungen.

**Fallbacks**
* Manuelle Statusabfrage: Statt automatischer Benachrichtigungen wird der Uploadstatus auf einer Statusseite für den Nutzer abrufbar gemacht.
* Implementierung eines Zeitintervalls für Benachrichtigungen, um die Belastung des Systems zu reduzieren.
* Fehlerlogging: Falls Benachrichtigungen nicht zugestellt werden, wird der Fehler geloggt und der Nutzer kann über einen Support-Kanal benachrichtigt werden.

**Ausführung des POCs**
* Klonen und installieren des Repositorys
* Starten der Anwendung
* in einer Konsole den folgenden Befehl eingeben (curl muss installiert sein):curl -X POST -F "image=@**(hier Link zum Bild)**" -F "quality=75" http://localhost:8080/api/image-compression/compress 
* Auf der Konsole wird eine Benachrichtigung ausgegeben, ob die Komprimierung funktioniert hat und falls nicht, wobei der Fehler liegt
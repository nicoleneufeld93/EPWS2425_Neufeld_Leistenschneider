## Überprüfung des Dateiformats
**Beschreibung**

Das Ziel ist die Implementierung einer Funktion, die hochgeladene Dateien vor der Verarbeitung auf ihr Dateiformat überprüft. Nur unterstützte Formate wie Bilder, Videos und Audiodateien sollen akzeptiert werden, während andere Formate wie PDFs, Textdateien oder unbekannte Formate abgelehnt werden. Dadurch wird verhindert, dass das System versucht, nicht kompatible Dateien zu komprimieren. Der Prototyp wird erstellt, um sicherzustellen, dass die Überprüfung zuverlässig funktioniert und unzulässige Dateiformate korrekt erkannt und abgelehnt werden.

**Exit-Kriterien**
* Das System erkennt unterstützte Dateiformate korrekt und lässt diese zu.
* Nicht unterstützte Formate werden zuverlässig abgelehnt, und der Nutzer erhält eine verständliche Fehlermeldung.
* Die Dateiformatprüfung erfolgt schnell und hat keinen spürbaren Einfluss auf die Uploadgeschwindigkeit.
* Es kommt zu keinem Versuch, unzulässige Dateien zu verarbeiten.

**Fail-Kriterien**
* Das System erkennt unterstützte Formate nicht und lehnt sie fälschlicherweise ab.
* Nicht unterstützte Formate werden fälschlicherweise akzeptiert und führen zu Fehlern bei der Verarbeitung.
* Die Überprüfung verlangsamt den Uploadprozess spürbar.
* Die Fehlermeldungen sind ungenau oder verwirrend, sodass der Nutzer nicht weiß, warum seine Datei abgelehnt wurde.

**Fallbacks**
* Anstelle einer detaillierten Formatprüfung wird eine einfache Whitelist verwendet, die nur bekannte und getestete Dateiformate akzeptiert.
* Manuelle Kontrolle: In einer Übergangsphase können unklare Dateiformate manuell vom Administrator geprüft werden, bevor sie verarbeitet werden.
* Vordefinierte Umleitung: Nicht unterstützte Dateien werden automatisch in einen separaten Ordner verschoben, mit einer Benachrichtigung an den Nutzer.
* Fehlerlog für Entwickler: Nicht unterstützte Formate werden geloggt, um die Liste der akzeptierten Formate bei Bedarf anzupassen und weiterzuentwickeln.

**Ausführung des POCs**
* Klonen und installieren des Repositorys
* Starten der Anwendung
* Testen der Anwendung mit Bilddateien & anderen Dateien (bspw. Textdatei)
* in einer Konsole den folgenden Befehl eingeben (curl muss installiert sein):curl -X POST -F "image=@**(hier Link zur Datei)**" -F "quality=75" http://localhost:8080/api/image-compression/compress 
* Im Ordner WebDEvProjekt/compressed-images befindet sich das komprimierte Bild, falls es eine Bilddatei war
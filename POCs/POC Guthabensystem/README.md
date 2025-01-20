## Guthabensystem
**Beschreibung**

Das Ziel ist die Entwicklung eines Guthabensystems, welches die Anzahl und Größe der hochgeladenen Dateien pro Nutzer überwacht. Das System soll überprüfen, ob das verbleibende Guthaben eines Nutzers ausreicht, um die aktuellen Uploads zu akzeptieren. Falls die Limits für die Dateianzahl oder -größe überschritten werden, wird eine Fehlermeldung ausgegeben. Das Guthaben soll nach jedem Upload automatisch aktualisiert werden.
Ein Prototyp wird entwickelt, der diese Berechnungen durchführt und den Nutzern Rückmeldungen in Echtzeit liefert, wenn ihre Uploadmöglichkeiten ausgeschöpft sind.

**Exit-Kriterien**
* Das Guthaben des Nutzers wird korrekt gegen die hochgeladenen Dateien verrechnet.
* Überschreitungen der Limits führen zuverlässig zu einer Fehlermeldung und verhindern den Upload.
* Das System ist in der Lage, unterschiedliche Limits für verschiedene Nutzer korrekt zu berücksichtigen.
* Das Guthabensystem aktualisiert sich nach jedem Upload automatisch und ohne Verzögerung.

**Fail-Kriterien**
* Das System verrechnet das Guthaben nicht korrekt (z. B. erlaubt es Uploads trotz ausgeschöpftem Guthaben).
* Fehlermeldungen werden nicht ausgegeben oder sind ungenau/unverständlich.
* Das Guthabensystem reagiert zu langsam oder stürzt bei großen Datenmengen ab.
* Es treten Fehler bei Nutzern mit individuell definierten Limits auf.

**Fallbacks**
* Manuelles Guthabenmanagement: Implementierung einer einfacheren Version, bei der das Guthaben manuell durch den Administrator angepasst werden kann, bis das automatische System zuverlässig funktioniert.
* Schrittweises Hochladen: Nutzer können Dateien einzeln hochladen, und das Guthaben wird nach jedem Upload erneut geprüft.
* Limit-Benachrichtigungen: Statt Uploads zu blockieren, wird der Nutzer bei Überschreitung gewarnt und die Dateien für spätere manuelle Genehmigung durch einen Admin bereitgestellt.

**Ausführung des POCs**
* Klonen und installieren des Repositorys
* Anpassen der Variable guthaben auf über 0 oder 0
* Starten der Anwendung
* in einer Konsole den folgenden Befehl eingeben (curl muss installiert sein):curl -X POST -F "image=@**(hier Link zum Bild)**" -F "quality=75" http://localhost:8080/api/image-compression/compress 
* falls das Guthaben>0, befindet sich im Ordner WebDevProjekt/dateikomprimierung/compressed-images das komprimierte Bild, falls das Guthaben nicht reicht, kommt eine Fehlermeldung
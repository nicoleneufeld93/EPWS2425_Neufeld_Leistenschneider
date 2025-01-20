## große Dateien und gleichzeitige Systemzugriffe

**Beschreibung**

Das Ziel ist die Entwicklung eines Systems, welches zuverlässig mehrere große Dateien gleichzeitig verarbeiten kann, auch wenn diese von verschiedenen Benutzern hochgeladen werden. Das System soll Lastspitzen bewältigen und sicherstellen, dass die parallelen Uploads stabil und fehlerfrei funktionieren. Die Performance und Stabilität werden unter realistischen Belastungsszenarien getestet.
Ein Prototyp wird entwickelt, bei dem mehrere Testbenutzer gleichzeitig verschiedene Dateien hochladen, um die Systemleistung, Geschwindigkeit und Fehleranfälligkeit zu überprüfen. Dabei werden auch Szenarien mit besonders großen Dateien simuliert.

**Exit-Kriterien**
* Das System verarbeitet parallele Uploads stabil, ohne dass es zu Abstürzen oder Verzögerungen kommt.
* Große Dateien können fehlerfrei hochgeladen und verarbeitet werden.
* Die Uploadgeschwindigkeit ist selbst bei parallelen Zugriffen für die Nutzer akzeptabel.
* Das System bleibt reaktionsfähig und erlaubt weiterhin die Nutzung anderer Funktionen während der Uploads.

**Fail-Kriterien**
* Das System stürzt ab oder funktioniert nicht mehr zuverlässig bei mehreren gleichzeitigen Zugriffen.
* Uploads großer Dateien werden abgebrochen oder stark verlangsamt.
* Parallele Zugriffe führen zu Fehlern in der Dateiverarbeitung (z. B. Datenverlust oder fehlerhafte Dateien).
* Die Systemreaktionszeit verschlechtert sich merklich für andere Nutzerfunktionen.

**Fallbacks**
* Warteschlange: Einführung eines Warteschlangensystems, um parallele Uploads zu regulieren und nacheinander zu verarbeiten, wenn die Systemkapazität überschritten wird.
* Skalierung der Serverleistung: Temporäre Skalierung der Serverressourcen, um Lastspitzen besser zu bewältigen.
* Dateiteilung: Große Dateien werden in kleinere Teile aufgeteilt, um den Upload zu erleichtern und den Ressourcenbedarf gleichmäßiger zu verteilen.
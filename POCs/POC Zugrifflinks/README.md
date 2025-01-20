## Zugrifflinks
**Beschreibung:** 

Das Ziel ist die Entwicklung eines Systems, welches individuelle Zugriffslinks für jeden Nutzer generieren und verwalten kann. Die Links müssen ermöglichen, Benutzeranpassungen (z. B. Dateilimits, Nutzungszeit, Optimierungsparameter) festzulegen. Jeder Endnutzer erhält einen personalisierten Link, welcher auf seine Wünsche individuelle Einstellungen bereitstellt. Ein Prototyp wird erstellt, welcher Zugriffslinks mit unterschiedlichen Parametern generiert und diese für verschiedene Testbenutzer bereitstellt. Die Funktionalität der Links wird überprüft, um sicherzustellen, dass die gewünschten Parameter korrekt umgesetzt werden.

**Exit-Kriterien:**
* Zugriffslinks können für Testnutzer mit korrekten Parametern generiert und verändert werden.
* Die Links ermöglichen den Zugriff auf die vorgesehenen Einstellungen.
* Es wird sichergestellt, dass die Parameter für jeden Link korrekt zugeordnet werden.
* Die Links sind abgesichert und die Endnutzer können keinen Zugriff auf andere Inhalte erlangen.

**Fail-Kriterien:**
* Zugriffslinks können nicht eindeutig generiert werden und die Parameter funktionieren nicht korrekt.
* Die Links erlauben Zugriff auf Inhalte oder Einstellungen, die nicht für den jeweiligen Endnutzer bestimmt sind.
* Die Verwaltung der Links ist nicht möglich, z.B. das Löschen oder Ändern der Parameter nach der Generierung.

**Fallbacks:**

* andere Authentifizierung: Statt der Überprüfung des Links könnte ein Token System eingesetzt werden, bei dem die Benutzerrechte serverseitig geprüft werden.
* Datenbank: Falls Parameter nicht korrekt über Links überprüft werden können, kann eine Datenbank genutzt werden, welche die Links mit den gewünschten Parametern verbindet.
* Fallback-Links: Als Übergangslösung können Standardlinks mit minimaler Anpassung eingesetzt werden, bis die individuelle Generierung funktioniert.

**Ausführung des POCs**
* Die Erstellung eines Links funktioniert noch nicht richtig mit der Speicherung in einer persistenten H2 Datenbank
<>---
# These are optional metadata elements. Feel free to remove any of them.
status: accepted
date: 04.12.2024
decision-makers: Nicole Neufeld
---

# Entscheidung für React als Frontend-Framework

## Context and Problem Statement

Im Rahmen eines Uni-Projekts wird eine CMS-Webseite entwickelt, die es Nutzern ermöglicht, Dateien hochzuladen, zu optimieren und die optimierten Dateien herunterzuladen. Die Webseite muss ein Admin-Dashboard enthalten, um Presets auszuwählen, Links zu generieren und die Dateioptimierung zu verwalten. Zudem muss die Seite interaktive Funktionen wie Drag-and-Drop und die Anzeige von Statusmeldungen bieten. Es muss ein Framework ausgewählt werden, das für diese interaktive Benutzeroberfläche geeignet ist.

## Decision Drivers

* Die Benutzeroberfläche sollte intuitiv und einfach zu bedienen sein, um ein gutes Nutzererlebnis zu gewährleisten.
* Funktionen wie Drag-and-Drop erfordern eine schnelle und effiziente Verwaltung des UI-Zustands.
* Es sollte einfach sein, das System in Zukunft mit weiteren Features zu erweitern.
* Ein gutes Ökosystem und eine starke Community helfen bei der Lösung von Problemen und der schnellen Umsetzung von Ideen.


## Considered Options

* **React** – Ein populäres JavaScript-Framework, das auf Komponenten basiert und eine gute Unterstützung für die Entwicklung interaktiver und zustandsgesteuerter UIs bietet.
* **Vue.js** – Ein progressives Framework, das eine einfache Lernkurve hat, aber nicht so viele Tools und Bibliotheken wie React bietet.
* **Angular** – Ein vollständiges Framework von Google, das viele Funktionen mitbringt, aber eine steilere Lernkurve hat und für ein kleines Projekt überdimensioniert wirken könnte.

## Decision Outcome

Chosen option: **React**, da es perfekt zu den Anforderungen des Projekts passt. React ermöglicht eine schnelle Entwicklung mit einem komponentenbasierten Ansatz und ist besonders gut für interaktive Webanwendungen geeignet. Für das geplante CMS-Projekt, das eine dynamische Benutzeroberfläche mit Drag-and-Drop, Statusanzeigen und Dateioptimierungsfunktionen benötigt, ist React aufgrund seiner Flexibilität eine gute Wahl.

* **Gut**, weil React es ermöglicht, wiederverwendbare Komponenten zu erstellen, was die Wartbarkeit und Erweiterbarkeit des Projekts fördert.
* **Gut**, weil die React-Community viele Lösungen und Bibliotheken bietet, die die Entwicklung beschleunigen (z.B. React-Dropzone für Drag-and-Drop).
* **Gut**, weil Reacts Virtual DOM eine gute Performance bei der Aktualisierung der Benutzeroberfläche ermöglicht.
* **Schlecht**, weil React eine zusätzliche Build- und Konfigurationsphase (z.B. mit Webpack und Babel) erfordert, was für kleinere Projekte eine gewisse Komplexität hinzufügt.
* **Schlecht**, weil die Lernkurve für Anfänger im Vergleich zu anderen Frameworks wie Vue.js etwas steiler sein kann.

### Confirmation

Wie stellen wir sicher, dass die Entscheidung, React zu verwenden, auch wirklich umgesetzt wird?

* **Code-Überprüfung:** Der Code wird regelmäßig im Team überprüft, um sicherzustellen, dass wir die React-Standards einhalten und der Code gut strukturiert bleibt.

* **Benutzeroberflächen-Test:** Die Benutzeroberfläche wird getestet, um sicherzustellen, dass sie einfach zu bedienen ist und alles funktioniert.

* **Überprüfung der Anforderungen:** Wir vergleichen die Entscheidung mit den ursprünglichen Anforderungen des Projekts, um sicherzustellen, dass sie zu den Zielen passt.

## Pros and Cons of the Options

### React

**Pro**
* Weit verbreitet und gut dokumentiert
* **Komponentenbasiert und Skalierbarkeit**: React teilt die Anwendung in kleine, wiederverwendbare Komponenten, was den Code sauber und wartbar hält.
* **Interaktive Benutzeroberflächen**: React bietet eine einfache Möglichkeit, komplexe und interaktive UI-Elemente zu erstellen

**Contra**
* **Komplexität für kleinere Projekte**: Wenn das Projekt klein bleibt, könnte React unnötig komplex sein und mehr Aufwand verursachen als nötig.
* **Erfordert zusätzliche Tools**: Für eine vollständige Entwicklung benötigen wir zusätzliche Tools wie Webpack und Babel, was den initialen Aufwand erhöht.

### Vue.js

**Pro**
* **Einfachere Integration und Setup:** Vue.js lässt sich schnell in bestehende Projekte integrieren und ist besonders gut für kleinere bis mittelgroße Anwendungen geeignet
* weniger umfassend als React

**Contra**
* weniger verbreitet
* schwieriger zu skalieren

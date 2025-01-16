<>---
status: accepted
date: 16.01.2025
decision-makers: Nicole Neufeld
---

# Entscheidung für Vanilla Code anstelle von React

## Context and Problem Statement

Im Rahmen eines Uni-Projekts wird eine CMS-Webseite entwickelt, die es Nutzern ermöglicht, Dateien hochzuladen, zu optimieren und die optimierten Dateien herunterzuladen. Die Webseite muss ein Admin-Dashboard enthalten, um Presets auszuwählen, Links zu generieren und die Dateioptimierung zu verwalten. Zudem muss die Seite interaktive Funktionen wie Drag-and-Drop und die Anzeige von Statusmeldungen bieten. Es muss ein Framework ausgewählt werden, das für diese interaktive Benutzeroberfläche geeignet ist.

## Decision Drivers

* Die Benutzeroberfläche sollte intuitiv und einfach zu bedienen sein, um ein gutes Nutzererlebnis zu gewährleisten.
* Funktionen wie Drag-and-Drop und Dateimanipulationen benötigen keine umfangreiche Framework-Abstraktion und sollten ohne viel Overhead realisiert werden.
* Eine einfache, wartbare Codebasis ohne unnötige Komplexität ist wichtig, um das Projekt effizient umzusetzen.
* Der Verzicht auf unnötige Tools und Build-Prozesse hilft, den Fokus auf die Kernfunktionalitäten zu legen und den Entwicklungsaufwand zu minimieren.

## Considered Options

* **React** – Ein populäres JavaScript-Framework, das auf Komponenten basiert und eine gute Unterstützung für die Entwicklung interaktiver und zustandsgesteuerter UIs bietet.
* **Vanilla Code** – Verwendung von reinem HTML, CSS und JavaScript, ohne zusätzliche Frameworks oder Bibliotheken.

## Decision Outcome

CGewählte Option: **Vanilla Code** (HTML, CSS und JavaScript), da dies am besten zu den spezifischen Anforderungen des Projekts passt. Für eine einfache Single-Page-Anwendung ohne komplexe Logik ist Vanilla Code aufgrund der geringen Komplexität die bessere Wahl.

* **Gut**, weil Vanilla Code keine zusätzlichen Bibliotheken oder Build-Prozesse erfordert, was die Komplexität reduziert und die Ladezeit der Seite verbessert.
* **Gut**, weil der Code klein und übersichtlich bleibt, was die Wartbarkeit und Erweiterbarkeit fördert.
* **Schlecht**, weil der Entwicklungsprozess für bestimmte interaktive Funktionen (wie Drag-and-Drop) etwas mehr Aufwand erfordert als in einem Framework, das solche Funktionen direkt unterstützt.

### Confirmation

Wie stellen wir sicher, dass die Entscheidung für Vanilla Code auch wirklich umgesetzt wird?

* **Code-Überprüfung:** Der Code wird regelmäßig im Team überprüft, um sicherzustellen, dass die grundlegenden JavaScript- und DOM-Manipulationen effektiv und wartbar sind.
* **Benutzeroberflächen-Test:** Die Benutzeroberfläche wird getestet, um sicherzustellen, dass sie einfach zu bedienen ist und alle Funktionen wie Upload, Statusanzeigen und Drag-and-Drop wie gewünscht funktionieren.
* **Überprüfung der Anforderungen:** Wir vergleichen die Entscheidung mit den ursprünglichen Anforderungen des Projekts, um sicherzustellen, dass sie zu den Zielen passt und die Nutzung von Vanilla Code keine unnötige Einschränkung darstellt.

## Pros and Cons of the Options

### Vanilla Code

**Pro**
* **Keine zusätzliche Komplexität:** Es wird keine zusätzliche Bibliothek oder Framework benötigt, was die Anwendung leichter und schneller macht.
* **Bessere Kontrolle:** Man kann die Interaktivität und Funktionalität genau nach Bedarf gestalten.
* **Schneller Einstieg:** Es sind keine externen Tools wie Webpack oder Babel erforderlich, was die Setup-Zeit minimiert.
* **Einfachere Wartung:** Der Code bleibt einfach und leicht nachvollziehbar, da keine externen Abstraktionen oder Konventionen von Frameworks vorhanden sind.

**Contra**
* **Mehr Entwicklungsaufwand für Interaktivität:** Bestimmte Features wie Drag-and-Drop oder Statusanzeigen müssen selbstständig implementiert werden, was zusätzlichen Aufwand bedeutet.
* **Weniger Flexibilität bei großen Erweiterungen:** Wenn das Projekt in Zukunft stark wächst, könnte es schwieriger sein, die Struktur mit reinem Vanilla Code zu skalieren, da moderne Frameworks viele nützliche Tools (wie State-Management) bereits bieten.

### React

### React

**Pro**
* **Wiederverwendbare Komponenten:** React bietet eine komponentenbasierte Struktur, die es leicht macht, wiederverwendbare UI-Elemente zu erstellen.
* **Große Community und Ökosystem:** Zahlreiche Bibliotheken und Lösungen (wie React-Dropzone) helfen, gängige Anforderungen schnell umzusetzen.
* **Virtual DOM für Performance:** React optimiert die Benutzeroberfläche und minimiert unnötige DOM-Manipulationen, was die Performance steigern kann.

**Contra**
* **Erhöhte Komplexität für kleinere Projekte:** React bringt zusätzliche Komplexität mit sich, die für dieses Projekt nicht notwendig ist.
* **Zusätzliche Build-Tools erforderlich:** React benötigt Tools wie Webpack und Babel, was für ein einfaches CMS unnötig viel Setup und Konfiguration bedeutet.

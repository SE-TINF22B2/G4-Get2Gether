# Get2Gether
Der Begleiter für deine Events!

Get2Gether ist das Hilfsmittel, wenn es um die Planung und Organisation von privaten Events geht.
Gemeinsam mit deinen Freunden kannst du auf der Weboberfläche von Get2Gether bequem deine nächsten Events planen und
Informationen austauschen.
Egal ob Party, Geburtstagsfeier oder Urlaub - dank der zahlreichen Funktionen von Get2Gether wird jedes Event ein Erfolg!

## Widgets
Mit den verschiedenen Widgets kannst du die Funktionalität für dein Event flexibel erweitern und
dir dein persönliches Dashboard zusammenstellen.

Die folgenden Widgets stehen derzeit zur Verfügung:
- Einkaufsliste
- Karte und Fahrgemeinschaft
- Bildergalerie
- Abstimmungen
- Kommentare
- Wetter

## Weitere Informationen
- ↪ [#️⃣ Dokumentation](https://github.com/SE-TINF22B2/G4-Get2Gether/discussions/categories/documentation)
- ↪ [📑 Guidelines](https://github.com/SE-TINF22B2/G4-Get2Gether/discussions/categories/guideline)
- ↪ [📔 Wiki](https://github.com/SE-TINF22B2/G4-Get2Gether/discussions/categories/wiki)
- ↪ [💬 Statusreports](https://github.com/SE-TINF22B2/G4-Get2Gether/discussions/categories/statusreports)
- ↪ [💻 Entwicklungsboard](https://github.com/orgs/SE-TINF22B2/projects/9)

## Entwicklung
Alles rund um die Entwicklung von Get2Gether.

### Repository Struktur
- 🍃 [backend](./backend) : Java Spring Backend
  - 🐋 [mongoDB.yml](./backend/mongoDB.yml) : Docker Compose Datei für lokale MongoDB
- 🅰️ [frontend](./frontend) : Angular Frontend

### Getting Started
Empfehlung des Hauses: Das Projekt kann direkt als Monorepo in Intellij importiert werden.
Intellij sollte dann alle gradle Module und Run Configurations automatisch importieren.

#### Frontend
Das Angular Frontend kann direkt mit der entsprechenden IntelliJ Run Configuration oder mit dem Befehl `ng serve`
im Ordner [frontend](./frontend) gestartet werden.
Das Frontend ist dann unter [http://localhost:4200](http://localhost:4200) erreichbar.

#### Backend
Um das Backend ausführen zu können, muss eine Verbindung zur MongoDB hergestellt werden können.
Dazu kann eine lokale MongoDB in Docker gestartet werden.
Eine Docker Compose Datei mit vorkonfigurierter MongoDB kann in [/backend/mongoDB.yml](./backend/mongoDB.yml) gefunden werden.

Das Spring Backend kann direkt mit der entsprechenden IntelliJ Run Configuration oder mit dem Ausführen der main-Methode
in der Klasse [BackendApplication](./backend/src/main/java/com/dhbw/get2gether/backend/BackendApplication.java)
gestartet werden.

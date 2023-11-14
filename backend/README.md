# 🍃 Get2Gether Backend
In diesem Verzeichnis befindet sich das Spring Boot Backend von Get2Gether.

## Getting Started
_Empfehlung des Hauses:_ Das Projekt kann direkt als Monorepo in Intellij importiert werden.
Intellij sollte dann alle gradle Module und Run Configurations automatisch importieren.

### Backend starten
Damit das Spring Backend gestartet werden kann, müssen folgende Voraussetzungen erfüllt sein.

Voraussetzungen:
- Verbindung zur MongoDB kann hergestellt werden. Eine Docker Compose Datei zum Starten einer lokalen MongoDB findet sich in [./mongoDB.yml](./mongoDB.yml).
- Secrets für die Google OAuth Authentifizierung sind vorhanden (siehe [application.properties](./src/main/resources/application.properties)).

Die Main-Klasse [BackendApplication](./src/main/java/com/dhbw/get2gether/backend/BackendApplication.java) kann mittels
der entsprechenden IntelliJ Run Configuration gestartet werden oder über den gradle task:
```bash
./gradlew bootRun
```

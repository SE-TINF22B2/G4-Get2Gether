# 🍃 Get2Gether Backend
In diesem Verzeichnis befindet sich das Spring Boot Backend von Get2Gether.

> [!TIP]
> 📖 [Hier geht's zur technischen Dokumentation des Backends.](https://github.com/SE-TINF22B2/G4-Get2Gether/wiki/Backend-Dokumentation)

## Getting Started
_Empfehlung:_ Das Projekt kann direkt als Monorepo in Intellij importiert werden.
Intellij sollte dann alle gradle Module und Run Configurations automatisch importieren.

### Voraussetzungen
- Java 17 (oder höher) installiert
- Docker
- Google OAuth API Token (mehr Informationen im Abschnitt [Secrets und Umgebungsvariablen](#secrets-und-umgebungsvariablen))

### Secrets und Umgebungsvariablen
Die vom Backend benötigten Secrets werden als Umgebungsvariablen eingebunden.
Dazu muss die `example.env` Datei zu `.env` kopiert werden. Die `.env` Datei ist vom git-Repository ausgenommen und sollte nicht committet werden.

Um die Umgebungsvariablen zur Laufzeit verfügbar zu machen, kann die `.env` Datei in der IntelliJ Run Configuration im Feld 'Environment variables' eingebunden werden.

Falls noch keine `GOOGLE_CLIENT_ID` und `GOOGLE_CLIENT_SECRET` vorhanden sind, muss in der Google Cloud Console eine neue OAuth2 Anwendung registriert werden.  
Mehr Informationen dazu können hier gefunden werden: [Setting up OAuth 2.0](https://support.google.com/cloud/answer/6158849)

### Backend starten
Damit das Spring Backend gestartet werden kann, müssen folgende Voraussetzungen erfüllt sein.

Voraussetzungen:
- Verbindung zur MongoDB kann hergestellt werden. Eine Docker Compose Datei zum Starten einer lokalen MongoDB findet sich in [./mongoDB.yml](./mongoDB.yml).
- Secrets für die Google OAuth2 Authentifizierung sind vorhanden.

Die Main-Klasse [BackendApplication](./src/main/java/com/dhbw/get2gether/backend/BackendApplication.java) kann mittels
der entsprechenden IntelliJ Run Configuration gestartet werden oder über den gradle task:
```bash
./gradlew bootRun
```

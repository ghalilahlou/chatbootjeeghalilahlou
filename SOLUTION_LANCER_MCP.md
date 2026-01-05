# Solution Complète pour Lancer le Serveur MCP

## Problème Identifié

Le serveur MCP ne se lance pas correctement car :
1. IntelliJ utilise le classpath du projet `chatboot` au lieu de `mcp-server`
2. Le serveur MCP essaie de se connecter en tant que client MCP (conflit d'auto-configuration)
3. Le fichier `McpServerApplication.java` était manquant

## Solution : Utiliser Maven depuis le Terminal (RECOMMANDÉ)

C'est la méthode la plus fiable et la plus simple.

### Étape 1 : Compiler le serveur MCP

```powershell
cd C:\Users\ghali\Documents\sonar\chatboot\mcp-server
mvn clean compile
```

### Étape 2 : Lancer le serveur MCP

**Terminal 1 - Serveur MCP (port 8989) :**
```powershell
cd C:\Users\ghali\Documents\sonar\chatboot\mcp-server
mvn spring-boot:run
```

Attendez de voir dans les logs :
```
Tomcat started on port(s): 8989 (http)
```

### Étape 3 : Lancer ChatbootApplication

**Terminal 2 - Chatboot (port 8087) :**
```powershell
cd C:\Users\ghali\Documents\sonar\chatboot
mvn spring-boot:run
```

## Solution Alternative : Scripts PowerShell Automatisés

### Script 1 : `start-mcp-server.ps1` (amélioré)

```powershell
# Script PowerShell pour démarrer le serveur MCP
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Démarrage du serveur MCP (port 8989)" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Aller dans le dossier mcp-server
$mcpServerPath = Join-Path $PSScriptRoot "mcp-server"
Set-Location $mcpServerPath

# Vérifier que Maven est disponible
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Host "ERREUR: Maven n'est pas installé ou n'est pas dans le PATH" -ForegroundColor Red
    exit 1
}

# Compiler et lancer
Write-Host "Compilation du serveur MCP..." -ForegroundColor Yellow
mvn clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERREUR: La compilation a échoué" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Démarrage du serveur MCP..." -ForegroundColor Green
Write-Host "Le serveur sera accessible sur http://localhost:8989" -ForegroundColor Cyan
Write-Host "Appuyez sur Ctrl+C pour arrêter le serveur" -ForegroundColor Yellow
Write-Host ""

mvn spring-boot:run
```

### Script 2 : `start-chatboot.ps1` (amélioré)

```powershell
# Script PowerShell pour démarrer l'application chatboot
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Démarrage de Chatboot (port 8087)" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Vérifier que le serveur MCP est démarré
Write-Host "Vérification du serveur MCP..." -ForegroundColor Yellow
$mcpResponse = $null
try {
    $mcpResponse = Invoke-WebRequest -Uri "http://localhost:8989/mcp" -Method GET -TimeoutSec 2 -ErrorAction Stop
    Write-Host "✓ Serveur MCP détecté sur le port 8989" -ForegroundColor Green
} catch {
    Write-Host "⚠ ATTENTION: Le serveur MCP ne semble pas être démarré sur le port 8989" -ForegroundColor Yellow
    Write-Host "  Assurez-vous de démarrer le serveur MCP en premier !" -ForegroundColor Yellow
    Write-Host "  Utilisez: .\start-mcp-server.ps1" -ForegroundColor Yellow
    Write-Host ""
    $continue = Read-Host "Voulez-vous continuer quand même ? (o/N)"
    if ($continue -ne "o" -and $continue -ne "O") {
        exit 1
    }
}

Write-Host ""
Write-Host "Démarrage de Chatboot..." -ForegroundColor Green
Write-Host "L'application sera accessible sur http://localhost:8087" -ForegroundColor Cyan
Write-Host "Appuyez sur Ctrl+C pour arrêter l'application" -ForegroundColor Yellow
Write-Host ""

# Retourner à la racine du projet
Set-Location $PSScriptRoot

# Démarrer l'application
mvn spring-boot:run
```

### Script 3 : `start-all.ps1` (lance les deux serveurs)

```powershell
# Script pour lancer les deux serveurs dans des fenêtres séparées
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Lancement des deux serveurs" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$scriptPath = $PSScriptRoot

# Lancer le serveur MCP dans une nouvelle fenêtre
Write-Host "Lancement du serveur MCP dans une nouvelle fenêtre..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$scriptPath\mcp-server'; Write-Host 'Serveur MCP (port 8989)' -ForegroundColor Green; mvn spring-boot:run"

# Attendre quelques secondes
Start-Sleep -Seconds 5

# Lancer Chatboot dans une nouvelle fenêtre
Write-Host "Lancement de Chatboot dans une nouvelle fenêtre..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$scriptPath'; Write-Host 'Chatboot (port 8087)' -ForegroundColor Green; mvn spring-boot:run"

Write-Host ""
Write-Host "✓ Les deux serveurs ont été lancés dans des fenêtres séparées" -ForegroundColor Green
Write-Host "  - Serveur MCP: http://localhost:8989" -ForegroundColor Cyan
Write-Host "  - Chatboot: http://localhost:8087" -ForegroundColor Cyan
```

## Vérification

Pour vérifier que les serveurs fonctionnent :

```powershell
# Vérifier le serveur MCP
curl http://localhost:8989/mcp

# Vérifier Chatboot
curl http://localhost:8087/api/health
```

## Dépannage

### Erreur : "Client failed to initialize"
- **Cause** : Le serveur MCP essaie de se connecter en tant que client
- **Solution** : Utilisez Maven depuis le terminal, pas IntelliJ directement

### Erreur : "Port already in use"
- **Cause** : Un processus utilise déjà le port
- **Solution** : 
  ```powershell
  # Trouver le processus
  netstat -ano | findstr :8989
  # Tuer le processus (remplacez PID par le numéro trouvé)
  taskkill /F /PID <PID>
  ```

### Erreur : "Unable to find main class"
- **Cause** : Le projet n'est pas compilé
- **Solution** : 
  ```powershell
  cd mcp-server
  mvn clean compile
  ```

## Structure du Projet

```
chatboot/
├── pom.xml                    # Projet chatboot (client MCP)
├── src/                       # Code source chatboot
├── mcp-server/               # Service MCP séparé
│   ├── pom.xml               # Projet mcp-server (serveur MCP)
│   └── src/                  # Code source mcp-server
│       └── main/java/org/example/mcpserver/
│           ├── McpServerApplication.java
│           └── tools/
│               └── McpTools.java
└── start-*.ps1               # Scripts de démarrage
```


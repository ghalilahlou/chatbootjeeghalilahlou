# Script PowerShell pour démarrer l'application chatboot
# Assurez-vous que le serveur MCP est déjà démarré sur le port 8989

Write-Host "Démarrage de l'application Chatboot..." -ForegroundColor Green
Write-Host "Assurez-vous que le serveur MCP est démarré sur le port 8989" -ForegroundColor Yellow
Write-Host ""

# Retourner à la racine du projet
Set-Location ..

# Démarrer l'application
mvn spring-boot:run


# Script PowerShell pour démarrer le serveur MCP
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Démarrage du serveur MCP (port 8989)" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Aller dans le dossier mcp-server
$mcpServerPath = Join-Path $PSScriptRoot "mcp-server"
if (-not (Test-Path $mcpServerPath)) {
    Write-Host "ERREUR: Le dossier mcp-server n'existe pas !" -ForegroundColor Red
    exit 1
}

Set-Location $mcpServerPath

# Vérifier que Maven est disponible
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Host "ERREUR: Maven n'est pas installé ou n'est pas dans le PATH" -ForegroundColor Red
    exit 1
}

# Compiler d'abord
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

# Démarrer le serveur
mvn spring-boot:run


# Configuration IntelliJ IDEA pour le projet Chatboot avec MCP

## Problème

IntelliJ essaie de lancer `McpServerApplication` depuis le projet `chatboot`, mais cette classe fait partie du module `mcp-server` qui est un projet Maven séparé.

## Solution Recommandée : Utiliser Maven depuis le Terminal

La solution la plus simple est d'utiliser Maven depuis le terminal PowerShell :

### Terminal 1 : Serveur MCP
```powershell
cd C:\Users\ghali\Documents\sonar\chatboot\mcp-server
mvn spring-boot:run
```

### Terminal 2 : Application Chatboot
```powershell
cd C:\Users\ghali\Documents\sonar\chatboot
mvn spring-boot:run
```

## Solution Alternative : Configurer IntelliJ

Si vous préférez utiliser IntelliJ pour lancer les applications :

### 1. Importer mcp-server comme module séparé

1. Dans IntelliJ, allez dans **File > Open**
2. Ouvrez le dossier `mcp-server` comme un projet séparé
3. IntelliJ devrait détecter automatiquement le `pom.xml` et configurer le projet

### 2. Créer des configurations d'exécution

#### Configuration pour McpServerApplication :
1. **Run > Edit Configurations...**
2. Cliquez sur **+** > **Application**
3. Configurez :
   - **Name**: `McpServerApplication`
   - **Module**: `mcp-server` (ou sélectionnez le module mcp-server)
   - **Main class**: `org.example.mcpserver.McpServerApplication`
   - **Working directory**: `$PROJECT_DIR$/mcp-server`

#### Configuration pour ChatbootApplication :
1. **Run > Edit Configurations...**
2. Cliquez sur **+** > **Application**
3. Configurez :
   - **Name**: `ChatbootApplication`
   - **Module**: `chatboot`
   - **Main class**: `org.example.chatboot.ChatbootApplication`
   - **Working directory**: `$PROJECT_DIR$`

### 3. Lancer les deux applications

1. Lancez d'abord `McpServerApplication` (port 8989)
2. Ensuite, lancez `ChatbootApplication` (port 8087)

## Note Importante

**L'ordre de démarrage est crucial** :
1. **D'abord** : Serveur MCP (port 8989)
2. **Ensuite** : Application Chatboot (port 8087)

L'application Chatboot a besoin que le serveur MCP soit déjà démarré pour se connecter et récupérer les outils.


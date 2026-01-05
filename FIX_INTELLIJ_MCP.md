# Solution : Configurer IntelliJ pour McpServerApplication

## Problème
IntelliJ essaie de lancer `McpServerApplication` avec le classpath du projet `chatboot`, mais cette classe appartient au module `mcp-server` qui est un projet Maven séparé.

## Solution 1 : Ouvrir mcp-server comme projet séparé (RECOMMANDÉ)

1. **Fermer le projet actuel** (optionnel, vous pouvez garder les deux ouverts)
2. **File > Open**
3. Naviguez vers : `C:\Users\ghali\Documents\sonar\chatboot\mcp-server`
4. Sélectionnez le dossier `mcp-server`
5. IntelliJ détectera automatiquement le `pom.xml` et configurera le projet
6. Attendez que Maven importe les dépendances
7. Maintenant vous pouvez lancer `McpServerApplication` normalement

## Solution 2 : Configurer le module dans le projet actuel

Si vous voulez garder les deux projets dans la même fenêtre IntelliJ :

### Étape 1 : Importer mcp-server comme module Maven

1. **File > Project Structure** (ou `Ctrl+Alt+Shift+S`)
2. Cliquez sur **Modules** dans la liste de gauche
3. Cliquez sur le **+** en haut
4. Sélectionnez **Import Module**
5. Naviguez vers : `C:\Users\ghali\Documents\sonar\chatboot\mcp-server`
6. Sélectionnez le dossier `mcp-server`
7. Choisissez **Import module from external model** > **Maven**
8. Cliquez sur **Next** et suivez l'assistant
9. Cliquez sur **Finish**

### Étape 2 : Créer une configuration d'exécution pour McpServerApplication

1. **Run > Edit Configurations...**
2. Cliquez sur le **+** en haut à gauche
3. Sélectionnez **Application**
4. Configurez :
   - **Name**: `McpServerApplication`
   - **Module**: Sélectionnez `mcp-server` (le module que vous venez d'importer)
   - **Main class**: `org.example.mcpserver.McpServerApplication`
   - **Working directory**: `$MODULE_DIR$` ou `C:\Users\ghali\Documents\sonar\chatboot\mcp-server`
5. Cliquez sur **OK**

### Étape 3 : Vérifier que le module est bien configuré

1. **File > Project Structure** > **Modules**
2. Sélectionnez `mcp-server`
3. Vérifiez que dans l'onglet **Sources**, le dossier `src/main/java` est marqué comme **Sources**
4. Vérifiez que dans l'onglet **Dependencies**, les dépendances Maven sont présentes

## Solution 3 : Utiliser Maven depuis le terminal (PLUS SIMPLE)

La solution la plus simple reste d'utiliser Maven depuis PowerShell :

```powershell
# Terminal 1 : Serveur MCP
cd C:\Users\ghali\Documents\sonar\chatboot\mcp-server
mvn spring-boot:run

# Terminal 2 : Application Chatboot
cd C:\Users\ghali\Documents\sonar\chatboot
mvn spring-boot:run
```

## Vérification

Une fois configuré, quand vous lancez `McpServerApplication` depuis IntelliJ, vous devriez voir dans le classpath des JARs du projet `mcp-server`, pas ceux de `chatboot`.

## Note importante

Si IntelliJ affiche toujours une erreur après configuration, essayez :
1. **File > Invalidate Caches / Restart...** > **Invalidate and Restart**
2. **Maven > Reload Project** (cliquez sur l'icône Maven dans la barre latérale)


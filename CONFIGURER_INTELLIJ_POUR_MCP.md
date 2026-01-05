# Guide Complet : Configurer IntelliJ pour lancer McpServerApplication

## Problème Identifié

IntelliJ essaie de lancer `McpServerApplication` avec le classpath du projet `chatboot`, mais cette classe appartient au module `mcp-server` qui est un projet Maven séparé.

## Solution : Importer mcp-server comme Module Maven

### Étape 1 : Importer le module mcp-server dans IntelliJ

1. **Ouvrez IntelliJ IDEA**
2. Allez dans **File > Project Structure** (ou `Ctrl+Alt+Shift+S`)
3. Dans la fenêtre qui s'ouvre, cliquez sur **Modules** dans la liste de gauche
4. Cliquez sur le bouton **+** en haut de la fenêtre
5. Sélectionnez **Import Module**
6. Naviguez vers : `C:\Users\ghali\Documents\sonar\chatboot\mcp-server`
7. Sélectionnez le dossier `mcp-server` et cliquez sur **OK**
8. Dans la fenêtre suivante, sélectionnez **Import module from external model**
9. Choisissez **Maven** et cliquez sur **Next**
10. Laissez les options par défaut et cliquez sur **Next**
11. Vérifiez que le module `mcp-server` est sélectionné et cliquez sur **Next**
12. Cliquez sur **Finish**

### Étape 2 : Vérifier que Maven a importé les dépendances

1. Dans IntelliJ, regardez la barre latérale droite (ou en bas)
2. Cliquez sur l'onglet **Maven** (ou allez dans **View > Tool Windows > Maven**)
3. Vous devriez voir deux projets :
   - `chatboot`
   - `mcp-server`
4. Cliquez sur le **+** à côté de `mcp-server` pour voir ses modules
5. Si vous voyez une icône de rafraîchissement, cliquez dessus pour recharger le projet Maven

### Étape 3 : Créer une Configuration d'Exécution

1. Allez dans **Run > Edit Configurations...** (ou `Shift+Alt+F10` puis `E`)
2. Cliquez sur le **+** en haut à gauche
3. Sélectionnez **Spring Boot**
4. Configurez la configuration :
   - **Name**: `McpServerApplication`
   - **Module**: Sélectionnez `mcp-server` dans la liste déroulante
   - **Main class**: Cliquez sur le bouton avec les trois points `...` et recherchez `org.example.mcpserver.McpServerApplication`
   - **Working directory**: `$MODULE_DIR$` (ou `C:\Users\ghali\Documents\sonar\chatboot\mcp-server`)
5. Cliquez sur **OK**

### Étape 4 : Vérifier que le module est bien configuré

1. **File > Project Structure** > **Modules**
2. Sélectionnez `mcp-server`
3. Allez dans l'onglet **Sources**
4. Vérifiez que `src/main/java` est marqué comme **Sources** (en bleu)
5. Vérifiez que `src/main/resources` est marqué comme **Resources** (en vert)
6. Allez dans l'onglet **Dependencies**
7. Vérifiez que les dépendances Maven sont présentes

### Étape 5 : Compiler le module mcp-server

1. **Build > Rebuild Project** (ou `Ctrl+Shift+F9`)
2. Ou allez dans **Maven** > `mcp-server` > **Lifecycle** > **compile**

### Étape 6 : Lancer McpServerApplication

1. Sélectionnez la configuration `McpServerApplication` dans la barre d'outils en haut
2. Cliquez sur le bouton **Run** (ou `Shift+F10`)

## Alternative : Utiliser Maven depuis IntelliJ

Si la configuration ci-dessus ne fonctionne pas, vous pouvez utiliser Maven directement depuis IntelliJ :

1. Ouvrez l'onglet **Maven** (barre latérale droite)
2. Développez `mcp-server` > **Plugins** > **spring-boot**
3. Double-cliquez sur **spring-boot:run**

## Vérification

Quand vous lancez `McpServerApplication`, vous devriez voir dans les logs :
- `Starting McpServerApplication`
- `Tomcat started on port(s): 8989 (http)`
- `Registered tools: 2`

## Si ça ne fonctionne toujours pas

1. **File > Invalidate Caches / Restart...**
2. Sélectionnez **Invalidate and Restart**
3. Attendez qu'IntelliJ redémarre
4. Réessayez les étapes ci-dessus

## Note Importante

**L'ordre de démarrage est crucial** :
1. **D'abord** : Lancez `McpServerApplication` (port 8989)
2. **Ensuite** : Lancez `ChatbootApplication` (port 8087)


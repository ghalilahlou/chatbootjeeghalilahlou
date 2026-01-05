# Solution : Configurer mcp-server comme Module Maven dans IntelliJ

## Problème

IntelliJ ne reconnaît pas `mcp-server` comme un **module Maven séparé**. Il le traite comme un simple dossier Java dans le projet `chatboot`, ce qui fait qu'il utilise le mauvais classpath (celui de chatboot avec les dépendances client MCP).

## Solution : Importer mcp-server comme Module Maven

### Méthode 1 : Via la Fenêtre Maven (RECOMMANDÉ)

1. **Ouvrir la fenêtre Maven** :
   - Cliquez sur l'onglet **Maven** en bas à droite
   - Ou : **View > Tool Windows > Maven**

2. **Ajouter le projet Maven** :
   - Cliquez sur le bouton **+** (Add Maven Project) en haut de la fenêtre Maven
   - Naviguez vers : `C:\Users\ghali\Documents\sonar\chatboot\mcp-server\pom.xml`
   - Sélectionnez le fichier `pom.xml` et cliquez sur **OK**

3. **Vérifier** :
   - Vous devriez maintenant voir deux projets dans la fenêtre Maven :
     - `chatboot`
     - `mcp-server`

4. **Lancer le serveur MCP** :
   - Développez `mcp-server` dans la fenêtre Maven
   - Développez **Plugins** > **spring-boot**
   - Double-cliquez sur **spring-boot:run**

### Méthode 2 : Importer comme Module dans Project Structure

1. **Ouvrir Project Structure** :
   - **File > Project Structure** (ou `Ctrl+Alt+Shift+S`)

2. **Ajouter un Module** :
   - Cliquez sur **Modules** dans la liste de gauche
   - Cliquez sur le **+** en haut
   - Sélectionnez **Import Module**

3. **Sélectionner le projet Maven** :
   - Naviguez vers : `C:\Users\ghali\Documents\sonar\chatboot\mcp-server`
   - Sélectionnez le dossier `mcp-server`
   - Cliquez sur **OK**

4. **Choisir le type de module** :
   - Sélectionnez **Import module from external model**
   - Choisissez **Maven**
   - Cliquez sur **Next**

5. **Configurer** :
   - Laissez les options par défaut
   - Cliquez sur **Next** jusqu'à **Finish**

6. **Vérifier** :
   - Dans **Project Structure > Modules**, vous devriez voir :
     - `chatboot`
     - `mcp-server`

### Méthode 3 : Créer une Configuration d'Exécution Maven

1. **Run > Edit Configurations...**

2. **Ajouter une configuration Maven** :
   - Cliquez sur **+** > **Maven**

3. **Configurer** :
   - **Name**: `McpServerApplication (Maven)`
   - **Working directory**: `$PROJECT_DIR$/mcp-server`
   - **Command line**: `spring-boot:run`
   - Cliquez sur **OK**

4. **Lancer** :
   - Sélectionnez la configuration `McpServerApplication (Maven)`
   - Cliquez sur **Run**

## ✅ Vérification

Pour vérifier que `mcp-server` est bien reconnu comme module Maven :

1. **Fenêtre Maven** : Vous devriez voir `mcp-server` avec ses dépendances
2. **Project Structure** : Le module `mcp-server` devrait apparaître
3. **Lancement** : Le serveur devrait démarrer sur le port **8989** (pas 8087)

## 🎯 Solution la Plus Simple

**Utilisez la fenêtre Maven d'IntelliJ** :
1. Ouvrez **Maven** (View > Tool Windows > Maven)
2. Ajoutez `mcp-server/pom.xml` avec le bouton **+**
3. Lancez via **mcp-server > Plugins > spring-boot > spring-boot:run**

C'est la méthode la plus fiable car elle utilise directement Maven sans passer par les configurations d'exécution Java d'IntelliJ.


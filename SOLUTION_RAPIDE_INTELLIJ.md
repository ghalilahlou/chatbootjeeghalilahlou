# Solution Rapide : Lancer McpServerApplication depuis IntelliJ

## Méthode 1 : Utiliser Maven directement dans IntelliJ (PLUS SIMPLE)

1. **Ouvrez l'onglet Maven** dans IntelliJ (barre latérale droite, ou **View > Tool Windows > Maven**)
2. Si vous ne voyez pas `mcp-server`, cliquez sur le bouton **+** et ajoutez le `pom.xml` de mcp-server :
   - Naviguez vers `C:\Users\ghali\Documents\sonar\chatboot\mcp-server\pom.xml`
   - Sélectionnez-le et cliquez sur **OK**
3. Dans l'arborescence Maven, développez :
   - `mcp-server` > **Plugins** > **spring-boot** > **spring-boot:run**
4. **Double-cliquez sur `spring-boot:run`**

Cela lancera le serveur MCP directement avec Maven, sans problème de classpath !

## Méthode 2 : Créer une Configuration Maven

1. **Run > Edit Configurations...**
2. Cliquez sur **+** > **Maven**
3. Configurez :
   - **Name**: `McpServerApplication (Maven)`
   - **Working directory**: `C:\Users\ghali\Documents\sonar\chatboot\mcp-server`
   - **Command line**: `spring-boot:run`
4. Cliquez sur **OK**
5. Lancez cette configuration

## Méthode 3 : Importer mcp-server comme Module (Solution Complète)

Suivez le guide détaillé dans `CONFIGURER_INTELLIJ_POUR_MCP.md`

## Pourquoi ces méthodes fonctionnent

- **Méthode 1 et 2** : Utilisent Maven directement, donc pas de problème de classpath
- **Méthode 3** : Configure IntelliJ pour reconnaître mcp-server comme module séparé

## Recommandation

**Utilisez la Méthode 1** - c'est la plus simple et la plus fiable !


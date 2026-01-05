# Guide de Démarrage - Chatbot avec MCP

Ce guide explique comment démarrer votre application chatbot avec MCP (Model Context Protocol).

## Architecture

Votre projet contient deux applications :

1. **mcp-server** : Serveur MCP qui expose des outils (port 8989)
2. **chatboot** : Application principale avec le chatbot qui utilise les outils MCP (port 8087)

## Prérequis

- Java 17 ou supérieur
- Maven installé
- Clé API OpenAI configurée dans `application.properties`

## Étapes de Démarrage

### Étape 1 : Démarrer le Serveur MCP

Le serveur MCP doit être démarré **AVANT** l'application principale.

```bash
# Naviguer vers le dossier mcp-server
cd mcp-server

# Compiler et démarrer le serveur MCP
mvn spring-boot:run
```

Le serveur MCP démarre sur le port **8989** et expose les outils suivants :
- `getEmployee(name)` : Récupère les informations d'un employé
- `getAllEmployees()` : Récupère la liste de tous les employés

**Vérification** : Le serveur est prêt quand vous voyez dans les logs :
```
Tomcat started on port(s): 8989 (http)
```

### Étape 2 : Démarrer l'Application Chatbot

Dans un **nouveau terminal**, démarrez l'application principale :

```bash
# Depuis la racine du projet
cd chatboot  # ou rester à la racine si vous êtes déjà dedans

# Compiler et démarrer l'application
mvn spring-boot:run
```

L'application démarre sur le port **8087**.

**Vérification** : L'application est prête quand vous voyez dans les logs :
- Les outils MCP disponibles listés
- `Tomcat started on port(s): 8087 (http)`

## Utilisation

### Via l'API REST

#### Test simple (GET)
```
http://localhost:8087/api/chat?query=Quels sont tous les employés?
```

#### Test avec POST
```bash
curl -X POST http://localhost:8087/api/chat \
  -H "Content-Type: application/json" \
  -d "{\"query\": \"Quel est le salaire de yahia?\"}"
```

### Via Telegram

1. Ouvrez Telegram
2. Recherchez votre bot
3. Envoyez des questions comme :
   - "Liste tous les employés"
   - "Quel est le salaire de yasmine?"
   - "Donne-moi les informations sur yassine"

## Exemples de Questions

Le chatbot peut maintenant utiliser les outils MCP pour répondre à des questions sur les employés :

- "Quels sont tous les employés de l'entreprise?"
- "Donne-moi les informations sur l'employé yahia"
- "Quel est le salaire de yasmine?"
- "Combien d'années d'ancienneté a yassine?"

## Dépannage

### Erreur : "Client failed to initialize by explicit API call"

**Cause** : Le serveur MCP n'est pas démarré ou n'est pas accessible.

**Solution** :
1. Vérifiez que le serveur MCP est bien démarré sur le port 8989
2. Vérifiez l'URL dans `application.properties` : `http://localhost:8989/mcp`
3. Assurez-vous que le serveur MCP est démarré **avant** l'application principale

### Erreur : "Connection refused"

**Cause** : Le port 8989 n'est pas accessible.

**Solution** :
1. Vérifiez qu'aucun autre processus n'utilise le port 8989
2. Redémarrez le serveur MCP

## Arrêt des Applications

Pour arrêter les applications :
- Appuyez sur `Ctrl+C` dans chaque terminal
- Ou arrêtez les processus Java correspondants

## Structure des Outils MCP

Les outils MCP sont définis dans `mcp-server/src/main/java/org/example/mcpserver/tools/McpTools.java` :

- **getEmployee(name)** : Retourne les informations d'un employé (nom, salaire, ancienneté)
- **getAllEmployees()** : Retourne la liste de tous les employés

Vous pouvez ajouter d'autres outils en créant de nouvelles méthodes annotées avec `@McpTool` dans cette classe.


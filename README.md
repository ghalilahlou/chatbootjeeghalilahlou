# Chatboot - Chatbot avec MCP et Telegram

## 📋 Description du Projet

**Chatboot** est un chatbot intelligent basé sur Spring AI qui utilise le **Model Context Protocol (MCP)** pour exposer et consommer des outils métier. Le projet permet d'interagir avec un système de gestion d'employés via :

- **API REST** (HTTP)
- **Telegram Bot** (interface conversationnelle)
- **Outils MCP** (gestion des employés)

Le projet implémente une architecture **client-serveur MCP** où le serveur expose des outils métier que le chatbot peut utiliser pour répondre intelligemment aux questions des utilisateurs.

## 🏗️ Architecture

Le projet est composé de **deux applications Spring Boot** indépendantes :

```
chatboot/
├── chatboot (Application principale - Port 8087)
│   ├── Client MCP
│   ├── Telegram Bot
│   ├── API REST
│   └── Intelligence conversationnelle (OpenAI/Ollama)
│
└── mcp-server (Serveur MCP - Port 8989)
    ├── Serveur MCP
    └── Outils métier exposés :
        ├── getEmployee(name)
        └── getAllEmployees()
```

### Flux de Données

```
Utilisateur (Telegram/API)
    ↓
Chatboot (port 8087)
    ↓
AI Model (OpenAI/Ollama) + MCP Tools
    ↓
Serveur MCP (port 8989)
    ↓
Données Employés
```

## 🚀 Démarrage Rapide

### Prérequis

- **Java 17** ou supérieur
- **Maven** installé
- **Clé API OpenAI** OU **Ollama** installé localement

### Démarrage avec Maven

**Terminal 1 - Serveur MCP (port 8989) :**
```powershell
cd mcp-server
mvn clean compile
mvn spring-boot:run
```

**Terminal 2 - Chatboot (port 8087) :**
```powershell
mvn spring-boot:run
```

### ⚠️ IMPORTANT

- **Ordre de démarrage** : Démarrer TOUJOURS le serveur MCP **AVANT** Chatboot
- **Ne pas utiliser IntelliJ directement** : IntelliJ utilise le mauvais classpath pour le serveur MCP. Préférez Maven depuis le terminal.

## ✅ Vérification

Une fois les deux serveurs démarrés :

- **Serveur MCP** : http://localhost:8989/mcp
- **Chatboot** : http://localhost:8087/api/health
- **Outils MCP disponibles** : Visible dans les logs de Chatboot

## 🔧 Configuration

### Configuration AI (OpenAI ou Ollama)

#### Option A : OpenAI (payant)

Dans `application.properties` :
```properties
spring.ai.openai.api-key=VOTRE_CLE_OPENAI
```

#### Option B : Ollama (gratuit, local)

1. **Installer Ollama** : https://ollama.com/download
2. **Télécharger un modèle** :
   ```powershell
   ollama pull llama3.2
   ```
3. **Configurer** dans `application.properties` :
   ```properties
   spring.ai.ollama.base-url=http://localhost:11434
   spring.ai.ollama.chat.options.model=llama3.2
   ```

**Avantages d'Ollama** :
- ✅ Gratuit et illimité
- ✅ Fonctionne localement (privacy)
- ✅ Pas besoin de clé API
- ✅ Fonctionne hors ligne

### Configuration Telegram

Dans `application.properties` :
```properties
telegram.api.key=VOTRE_CLE_API_TELEGRAM
telegram.bot.username=votre_nom_de_bot
```

### Configuration MCP

```properties
spring.ai.mcp.client.streamable-http.connections.mcprh.url=http://localhost:8989/mcp
```

## 💬 Utilisation

### 1. Via Telegram Bot

1. Ouvrez **Telegram**
2. Recherchez votre bot : `@votre_nom_de_bot`
3. Cliquez sur **Start**
4. Posez vos questions !

**Exemples de questions** :
- "Liste tous les employés"
- "Quel est le salaire de ghali ?"
- "Donne-moi les informations sur yamine"
- "Combien d'années d'ancienneté a sale ?"

### 2. Via API REST

**Test simple (GET)** :
```
http://localhost:8087/api/chat?message=Liste tous les employés
```

**Test avec POST** :
```powershell
curl -X POST http://localhost:8087/api/chat `
  -H "Content-Type: application/json" `
  -d '{"query": "Quel est le salaire de yahia?"}'
```

## 🛠️ Outils MCP Disponibles

Le serveur MCP expose les outils suivants :

### 1. `getEmployee(name: String)`
Récupère les informations d'un employé spécifique.

**Exemple** :
```json
{
  "name": "ghali",
  "salary": 5000,
  "yearsOfService": 3
}
```

### 2. `getAllEmployees()`
Récupère la liste de tous les employés.

**Exemple** :
```json
[
  {"name": "ghali", "salary": 5000, "yearsOfService": 3},
  {"name": "yamine", "salary": 4500, "yearsOfService": 2},
  {"name": "sale", "salary": 6000, "yearsOfService": 5}
]
```

## 🚨 Dépannage

### Le Bot Telegram ne répond pas

**Solutions** :
1. Vérifiez que Chatboot est démarré (port 8087)
2. Vérifiez que le serveur MCP est démarré (port 8989)
3. Vérifiez la clé API Telegram dans `application.properties`
4. Vérifiez les logs pour détecter les erreurs

### Erreur : "Connection refused"

**Cause** : Le serveur MCP n'est pas accessible.

**Solutions** :
1. Vérifiez que le serveur MCP est démarré
2. Vérifiez qu'il écoute bien sur le port 8989 : `netstat -ano | findstr :8989`
3. Redémarrez le serveur MCP

### Erreur : "Conflict: terminated by other getUpdates request"

**Cause** : Une autre instance du bot Telegram est déjà en cours.

**Solution** : Arrêtez toutes les instances de Chatboot et redémarrez.

### Le Bot répond mais sans utiliser les outils MCP

**Vérifications** :
1. Le serveur MCP est bien démarré
2. Les outils MCP apparaissent dans les logs de Chatboot
3. Les questions sont claires (ex: "Liste tous les employés")

### IntelliJ ne fonctionne pas

**Pourquoi ?** IntelliJ détecte `McpServerApplication` mais utilise le classpath de `chatboot` qui contient le client MCP au lieu du serveur MCP.

**Solution** :
- Utilisez **Maven depuis le terminal**
- OU ouvrez `mcp-server` comme projet séparé dans IntelliJ

## 📚 Technologies Utilisées

- **Spring Boot 3.x** - Framework Java
- **Spring AI** - Intégration AI (OpenAI, Ollama)
- **MCP (Model Context Protocol)** - Exposition et consommation d'outils
- **Telegram Bot API** - Interface conversationnelle
- **Maven** - Gestion des dépendances
- **Java 17** - Plateforme

## 📁 Structure du Projet

```
chatboot/
├── src/main/java/org/example/chatboot/
│   ├── ChatbootApplication.java          # Application principale
│   ├── ai/                                # Services AI
│   ├── telegram/                          # Bot Telegram
│   └── controller/                        # API REST
│
├── mcp-server/
│   └── src/main/java/org/example/mcpserver/
│       ├── McpServerApplication.java      # Serveur MCP
│       └── tools/
│           └── McpTools.java              # Outils MCP exposés
│
├── pom.xml                                # Dépendances Chatboot
└── mcp-server/pom.xml                     # Dépendances MCP Server
```

## 🎯 Fonctionnalités Clés

✅ **Chatbot Intelligent** - Répond en langage naturel grâce à OpenAI/Ollama  
✅ **MCP Tools** - Utilise des outils métier exposés via MCP  
✅ **Telegram Bot** - Interface conversationnelle accessible partout  
✅ **API REST** - Endpoints pour intégrations tierces  
✅ **Architecture Modulaire** - Client et serveur MCP séparés  
✅ **Multi-Provider AI** - Support OpenAI et Ollama  
✅ **Extensible** - Ajoutez facilement de nouveaux outils MCP  

## 🔐 Sécurité

- Clés API stockées dans `application.properties` (à ne pas commiter)
- Utilisez des variables d'environnement en production
- Le serveur MCP peut être sécurisé avec Spring Security si nécessaire

## 📝 Licence

Ce projet est à usage personnel et éducatif.

## 🤝 Contribution

Pour ajouter de nouveaux outils MCP :

1. Ajoutez une méthode dans `McpTools.java`
2. Annotez-la avec `@McpTool`
3. Redémarrez le serveur MCP
4. Les nouveaux outils seront automatiquement disponibles dans Chatboot

## 📞 Support

Pour toute question ou problème :
1. Consultez les logs des deux serveurs
2. Vérifiez que les ports 8087 et 8989 sont libres
3. Assurez-vous que l'ordre de démarrage est respecté (MCP puis Chatboot)

---

**Auteur** : Ghali Lahlou  
**Projet** : Chatboot avec MCP et Telegram

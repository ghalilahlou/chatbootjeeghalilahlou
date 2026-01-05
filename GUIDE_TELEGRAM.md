# Guide : Utiliser le Chatbot via Telegram

## ✅ Configuration Actuelle

Votre bot Telegram est déjà configuré et devrait fonctionner maintenant que les deux serveurs sont démarrés.

### Informations du Bot

- **Clé API** : Configurée dans `application.properties`
- **Nom d'utilisateur** : `chatboot_helper_bot` (par défaut)
- **Port** : 8087

## 🚀 Comment Utiliser le Bot

### Étape 1 : Vérifier que Chatboot est démarré

Assurez-vous que l'application Chatboot est en cours d'exécution sur le port 8087. Vous devriez voir dans les logs :
```
Tomcat started on port 8087 (http)
=== Outils MCP disponibles ===
```

### Étape 2 : Trouver votre Bot sur Telegram

1. **Ouvrez Telegram** (application mobile ou desktop)
2. **Recherchez votre bot** :
   - Dans la barre de recherche, tapez : `@chatboot_helper_bot`
   - Ou utilisez le nom d'utilisateur configuré dans `application.properties`

### Étape 3 : Démarrer une Conversation

1. **Cliquez sur le bot** dans les résultats de recherche
2. **Cliquez sur "Start"** ou envoyez `/start`
3. **Commencez à poser des questions** !

## 💬 Exemples de Questions

Le bot peut maintenant utiliser les outils MCP pour répondre à vos questions sur les employés :

### Questions sur les Employés

- **"Liste tous les employés"**
  - Le bot utilisera l'outil `getAllEmployees`

- **"Quel est le salaire de ghali ?"**
  - Le bot utilisera l'outil `getEmployee` avec le nom "ghali"

- **"Donne-moi les informations sur yamine"**
  - Le bot utilisera l'outil `getEmployee` et vous donnera le salaire et l'ancienneté

- **"Combien d'années d'ancienneté a sale ?"**
  - Le bot utilisera l'outil `getEmployee` pour trouver l'ancienneté

### Questions Générales

- **"Bonjour"**
- **"Comment ça va ?"**
- **"Qu'est-ce que tu peux faire ?"**

## 🔍 Vérification du Fonctionnement

### Vérifier que le Bot est Connecté

Dans les logs de Chatboot, vous devriez voir :
```
=== Outils MCP disponibles ===
----------------------
DefaultToolDefinition[name=getAllEmployees, ...]
----------------------
DefaultToolDefinition[name=getEmployee, ...]
----------------------
```

### Tester via l'API REST (Alternative)

Si le bot Telegram ne fonctionne pas, vous pouvez tester via l'API REST :

```powershell
# Test simple
curl http://localhost:8087/api/chat?message="Liste tous les employés"

# Ou via navigateur
http://localhost:8087/api/chat?message=Bonjour
```

## 🛠️ Dépannage

### Le Bot ne répond pas

1. **Vérifiez que Chatboot est démarré** :
   ```powershell
   # Vérifier le port 8087
   netstat -ano | findstr :8087
   ```

2. **Vérifiez les logs** :
   - Regardez les logs de Chatboot pour voir s'il y a des erreurs
   - Cherchez des messages comme "Failed to register Telegram bot"

3. **Vérifiez la clé API Telegram** :
   - La clé dans `application.properties` doit être valide
   - Format : `telegram.api.key=XXXXX:YYYYY`

4. **Vérifiez que le serveur MCP est démarré** :
   - Le bot a besoin du serveur MCP pour fonctionner
   - Vérifiez le port 8989 : `netstat -ano | findstr :8989`

### Erreur : "Conflict: terminated by other getUpdates request"

**Cause** : Un autre instance du bot est déjà en cours d'exécution.

**Solution** :
1. Arrêtez toutes les instances de Chatboot
2. Redémarrez uniquement une instance

### Le Bot répond mais sans utiliser les outils MCP

**Vérifiez** :
1. Le serveur MCP est démarré sur le port 8989
2. Les outils MCP sont bien détectés dans les logs
3. Les questions sont formulées clairement (ex: "Liste tous les employés")

## 📝 Configuration Avancée

### Changer le Nom d'Utilisateur du Bot

Dans `application.properties`, ajoutez :
```properties
telegram.bot.username=votre_nom_de_bot
```

### Activer les Logs Détaillés

Dans `application.properties`, ajoutez :
```properties
logging.level.org.example.chatboot.telegram=DEBUG
logging.level.org.example.chatboot.ai=DEBUG
```

## 🎯 Résumé

1. ✅ **Serveur MCP** démarré sur port 8989
2. ✅ **Chatboot** démarré sur port 8087
3. ✅ **Bot Telegram** configuré et enregistré
4. ✅ **Outils MCP** disponibles (getEmployee, getAllEmployees)

**Vous pouvez maintenant utiliser le bot sur Telegram !** 🎉


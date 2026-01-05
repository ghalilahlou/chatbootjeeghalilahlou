# 🚀 Configurer Ollama au lieu d'OpenAI

## Pourquoi Ollama ?

- ✅ **Gratuit** : Pas besoin de clé API payante
- ✅ **Local** : Fonctionne sur votre machine
- ✅ **Open Source** : Modèles libres
- ✅ **Compatible** : Spring AI supporte Ollama nativement

## 📋 Prérequis

### Étape 1 : Installer Ollama

1. **Téléchargez Ollama** :
   - Windows : https://ollama.com/download/windows
   - Mac : https://ollama.com/download/mac
   - Linux : https://ollama.com/download/linux

2. **Installez Ollama** :
   - Exécutez le fichier d'installation
   - Ollama démarrera automatiquement

3. **Téléchargez un modèle** :
   ```powershell
   # Modèle recommandé : Llama 3.2 (3B, rapide et efficace)
   ollama pull llama3.2
   
   # Ou pour un modèle plus puissant (mais plus lent)
   ollama pull llama3.1
   
   # Ou pour un modèle français
   ollama pull mistral
   ```

### Étape 2 : Vérifier l'Installation

```powershell
# Tester Ollama
ollama run llama3.2 "Bonjour, comment ça va ?"
```

Si cela fonctionne, Ollama est correctement installé !

## ⚙️ Configuration de l'Application

### Étape 1 : Mettre à jour `application.properties`

Remplacez la configuration OpenAI par :

```properties
# Configuration Ollama
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=llama3.2
```

**Modèles disponibles** :
- `llama3.2` - Rapide et efficace (recommandé)
- `llama3.1` - Plus puissant mais plus lent
- `mistral` - Bon pour le français
- `phi3` - Très rapide, léger

### Étape 2 : Redémarrer l'Application

1. **Arrêtez Chatboot** si il est en cours d'exécution
2. **Démarrez Ollama** (il devrait être déjà démarré)
3. **Redémarrez Chatboot**

## 🧪 Tester

### Test 1 : Vérifier qu'Ollama fonctionne

```powershell
# Dans un terminal
ollama list
```

Vous devriez voir les modèles téléchargés.

### Test 2 : Tester via l'API REST

```powershell
Invoke-WebRequest -Uri "http://localhost:8087/api/chat?query=Bonjour" | Select-Object -ExpandProperty Content
```

### Test 3 : Tester via Telegram

Envoyez un message à votre bot Telegram. Il devrait répondre !

## 🔄 Retour à OpenAI (si nécessaire)

Si vous voulez revenir à OpenAI plus tard :

1. **Remettez la dépendance OpenAI** dans `pom.xml`
2. **Commentez Ollama** dans `pom.xml`
3. **Mettez à jour `application.properties`** :
   ```properties
   spring.ai.openai.api-key=VOTRE_CLE_OPENAI
   ```
4. **Redémarrez l'application**

## 📝 Configuration Complète

### `application.properties` avec Ollama

```properties
spring.application.name=chatboot
server.port=8087

# Configuration Ollama
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=llama3.2

# Configuration MCP Client
spring.ai.mcp.client.streamable-http.connections.mcprh.url=http://localhost:8989/mcp

# Configuration Telegram
telegram.api.key=8571783033:AAHzmek6df009XwfImq_agqHVxcUDsHbTVU
```

## ⚠️ Notes Importantes

1. **Ollama doit être démarré** avant Chatboot
2. **Le modèle doit être téléchargé** (`ollama pull llama3.2`)
3. **Ollama utilise plus de RAM** qu'OpenAI (modèles locaux)
4. **Les réponses peuvent être plus lentes** qu'OpenAI (selon votre machine)

## 🎯 Avantages d'Ollama

- ✅ Pas de limite de requêtes
- ✅ Pas de coûts
- ✅ Données restent locales (privacy)
- ✅ Fonctionne hors ligne
- ✅ Pas besoin de clé API

## 🚨 Dépannage

### Erreur : "Connection refused"

**Solution** : Ollama n'est pas démarré. Démarrez-le manuellement ou vérifiez qu'il est en cours d'exécution.

### Erreur : "Model not found"

**Solution** : Le modèle n'est pas téléchargé. Exécutez :
```powershell
ollama pull llama3.2
```

### Réponses lentes

**Solution** : 
- Utilisez un modèle plus petit (`llama3.2` au lieu de `llama3.1`)
- Augmentez la RAM disponible
- Fermez d'autres applications

## 📚 Ressources

- Site officiel : https://ollama.com
- Modèles disponibles : https://ollama.com/library
- Documentation Spring AI : https://docs.spring.io/spring-ai/reference/


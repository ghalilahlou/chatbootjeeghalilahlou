# ⚡ Guide Rapide : Passer à Ollama

## 🎯 En 3 Étapes

### 1️⃣ Installer Ollama

**Windows** :
1. Téléchargez : https://ollama.com/download/windows
2. Installez et lancez Ollama
3. Téléchargez un modèle :
   ```powershell
   ollama pull llama3.2
   ```

### 2️⃣ Configuration Déjà Faite ✅

J'ai déjà :
- ✅ Ajouté la dépendance Ollama dans `pom.xml`
- ✅ Configuré `application.properties` pour utiliser Ollama
- ✅ Commenté la configuration OpenAI

### 3️⃣ Redémarrer

1. **Vérifiez qu'Ollama est démarré** (il devrait l'être après l'installation)
2. **Redémarrez Chatboot**
3. **Testez** : `http://localhost:8087/api/chat?query=Bonjour`

## ✅ C'est Tout !

Votre chatbot fonctionne maintenant avec Ollama (gratuit) au lieu d'OpenAI.

## 🧪 Vérifier qu'Ollama Fonctionne

```powershell
# Vérifier les modèles installés
ollama list

# Tester Ollama directement
ollama run llama3.2 "Bonjour"
```

## 📝 Modèles Recommandés

- **`llama3.2`** - Rapide et efficace (recommandé pour commencer)
- **`mistral`** - Bon pour le français
- **`phi3`** - Très rapide, léger

Pour changer de modèle, modifiez dans `application.properties` :
```properties
spring.ai.ollama.chat.options.model=llama3.2
```

## 🆘 Problème ?

Si vous avez une erreur "Connection refused" :
1. Vérifiez qu'Ollama est démarré
2. Vérifiez que le modèle est téléchargé : `ollama pull llama3.2`
3. Redémarrez Chatboot

Consultez `CONFIGURER_OLLAMA.md` pour plus de détails.



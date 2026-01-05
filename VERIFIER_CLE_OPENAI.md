# 🔍 Vérifier et Corriger la Clé API OpenAI

## Problème Actuel

L'erreur `Error while extracting response for type [OpenAiApi$ChatCompletion] and content type [text/plain]` indique que l'API OpenAI renvoie une réponse en texte brut au lieu de JSON. Cela signifie généralement que :

1. **La clé API est invalide ou expirée**
2. **La clé API est mal formatée** (espaces, caractères manquants)
3. **Vous n'avez plus de crédits** sur votre compte OpenAI
4. **La clé API a été révoquée**

## ✅ Solution : Vérifier et Mettre à Jour la Clé API

### Étape 1 : Obtenir une Nouvelle Clé API

1. Allez sur https://platform.openai.com/api-keys
2. Connectez-vous à votre compte OpenAI
3. Cliquez sur **"Create new secret key"**
4. **Copiez la clé** (vous ne pourrez la voir qu'une seule fois !)
5. Donnez-lui un nom (ex: "chatboot")

### Étape 2 : Mettre à Jour `application.properties`

1. Ouvrez `src/main/resources/application.properties`
2. Remplacez la ligne :
   ```properties
   spring.ai.openai.api-key=VOTRE_NOUVELLE_CLE_ICI
   ```
3. ⚠️ **Important** :
   - La clé doit commencer par `sk-`
   - Pas d'espaces dans la clé
   - Pas de guillemets
   - La clé doit être sur une seule ligne

### Étape 3 : Vérifier le Format

La clé API OpenAI doit ressembler à :
```
sk-proj-XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

**Format correct** :
```properties
spring.ai.openai.api-key=sk-proj-abc123def456...
```

**Format incorrect** :
```properties
# ❌ Avec espace
spring.ai.openai.api-key=sk-proj-abc123 def456...

# ❌ Avec guillemets
spring.ai.openai.api-key="sk-proj-abc123..."

# ❌ Sur plusieurs lignes
spring.ai.openai.api-key=sk-proj-abc123\
def456...
```

### Étape 4 : Redémarrer l'Application

Après avoir mis à jour la clé, **redémarrez Chatboot** pour que les changements prennent effet.

## 🧪 Tester la Clé API

### Test 1 : Via l'API REST

```powershell
Invoke-WebRequest -Uri "http://localhost:8087/api/chat?query=Bonjour" | Select-Object -ExpandProperty Content
```

Si cela fonctionne, la clé est valide.

### Test 2 : Vérifier les Logs

Regardez les logs au démarrage. Vous devriez voir :
- ✅ `=== Outils MCP disponibles ===`
- ✅ `✅ Bot Telegram enregistré avec succès`
- ❌ Pas d'erreur OpenAI

### Test 3 : Tester avec Telegram

1. Ouvrez Telegram
2. Envoyez un message à votre bot
3. Si le bot répond, la clé est valide
4. Si vous voyez un message d'erreur, la clé est invalide

## 🔧 Dépannage

### Erreur : "Invalid API Key"

**Solution** : La clé API est invalide. Créez-en une nouvelle sur https://platform.openai.com/api-keys

### Erreur : "Insufficient quota"

**Solution** : Vous n'avez plus de crédits. Ajoutez des crédits sur https://platform.openai.com/account/billing

### Erreur : "Rate limit exceeded"

**Solution** : Vous avez dépassé la limite de requêtes. Attendez quelques minutes ou passez à un plan supérieur.

### Erreur : "text/plain instead of JSON"

**Solution** : 
1. Vérifiez que la clé API est correctement formatée (pas d'espaces)
2. Vérifiez que la clé est active sur https://platform.openai.com/api-keys
3. Créez une nouvelle clé si nécessaire

## 📝 Checklist

- [ ] Clé API obtenue sur https://platform.openai.com/api-keys
- [ ] Clé API mise à jour dans `application.properties`
- [ ] Pas d'espaces dans la clé
- [ ] Clé sur une seule ligne
- [ ] Application redémarrée
- [ ] Test effectué via l'API REST
- [ ] Test effectué via Telegram

## 💡 Alternative : Utiliser l'API REST Sans OpenAI

Si vous ne pouvez pas utiliser OpenAI pour le moment, vous pouvez toujours tester le chatbot via l'API REST. Le bot répondra avec un message d'erreur, mais vous pourrez vérifier que :
- ✅ Le serveur MCP fonctionne
- ✅ Le bot Telegram est connecté
- ✅ L'application démarre correctement

Une fois la clé OpenAI corrigée, tout fonctionnera normalement.


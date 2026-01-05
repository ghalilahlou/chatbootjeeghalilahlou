# Dépannage : Problèmes avec Telegram et OpenAI

## 🔍 Problèmes Identifiés

### 1. Erreur OpenAI : `Error while extracting response for type [OpenAiApi$ChatCompletion] and content type [text/plain]`

**Cause** : La clé API OpenAI est invalide, expirée, ou mal formatée.

**Solutions** :
1. **Vérifier la clé API** :
   - Allez sur https://platform.openai.com/api-keys
   - Vérifiez que votre clé est active
   - Si elle est expirée, créez-en une nouvelle

2. **Mettre à jour la clé dans `application.properties`** :
   ```properties
   spring.ai.openai.api-key=VOTRE_CLE_API_ICI
   ```
   ⚠️ **Important** : La clé ne doit pas contenir d'espaces !

3. **Redémarrer l'application** après avoir mis à jour la clé

### 2. Erreur Telegram : `UnknownHostException: api.telegram.org`

**Causes possibles** :
- Pas de connexion Internet
- Problème DNS
- Firewall/Proxy bloquant Telegram
- VPN ou restrictions réseau

**Solutions** :

1. **Vérifier la connexion Internet** :
   ```powershell
   ping api.telegram.org
   ```

2. **Vérifier le DNS** :
   ```powershell
   nslookup api.telegram.org
   ```

3. **Si vous êtes derrière un proxy/firewall** :
   - Configurez les paramètres proxy dans votre application
   - Vérifiez que Telegram n'est pas bloqué

4. **Tester la connexion Telegram** :
   ```powershell
   curl https://api.telegram.org/bot8571783033:AAHzmek6df009XwfImq_agqHVxcUDsHbTVU/getMe
   ```

### 3. Erreur : `cannot retry due to server authentication, in streaming mode`

**Cause** : Problème d'authentification avec l'API OpenAI.

**Solutions** :
1. Vérifiez que votre clé API OpenAI est valide
2. Vérifiez que vous avez des crédits disponibles sur votre compte OpenAI
3. Vérifiez les limites de taux (rate limits) de votre compte

## ✅ Corrections Appliquées

J'ai corrigé les problèmes suivants :

1. **Clé API OpenAI** : Suppression de l'espace dans la clé API
2. **Gestion d'erreur Telegram** : Le bot gère maintenant les erreurs gracieusement
3. **Logs améliorés** : Meilleure visibilité des erreurs

## 🧪 Tester la Configuration

### Test 1 : Vérifier OpenAI

Testez via l'API REST :
```powershell
Invoke-WebRequest -Uri "http://localhost:8087/api/chat?query=Bonjour" | Select-Object -ExpandProperty Content
```

Si cela fonctionne, OpenAI est correctement configuré.

### Test 2 : Vérifier Telegram

1. **Vérifier que le bot est enregistré** :
   - Regardez les logs au démarrage
   - Vous devriez voir : `✅ Bot Telegram enregistré avec succès`

2. **Tester la connexion Telegram** :
   - Ouvrez Telegram
   - Recherchez votre bot
   - Envoyez un message `/start`

### Test 3 : Vérifier MCP

Vérifiez que le serveur MCP est démarré :
```powershell
netstat -ano | findstr :8989
```

## 🔧 Solutions Alternatives

### Si Telegram ne fonctionne pas

Vous pouvez toujours utiliser le chatbot via l'API REST :

1. **Via navigateur** :
   ```
   http://localhost:8087/api/chat?query=Liste tous les employés
   ```

2. **Via PowerShell** :
   ```powershell
   Invoke-WebRequest -Uri "http://localhost:8087/api/chat?query=Bonjour"
   ```

3. **Via curl** :
   ```bash
   curl "http://localhost:8087/api/chat?query=Bonjour"
   ```

## 📝 Checklist de Vérification

- [ ] Clé API OpenAI valide et sans espaces
- [ ] Connexion Internet active
- [ ] Serveur MCP démarré (port 8989)
- [ ] Chatboot démarré (port 8087)
- [ ] Bot Telegram enregistré (vérifier les logs)
- [ ] Pas de firewall/proxy bloquant Telegram

## 🆘 Si Rien Ne Fonctionne

1. **Vérifiez les logs** :
   - Regardez les erreurs dans la console
   - Cherchez les messages d'erreur spécifiques

2. **Testez chaque composant séparément** :
   - Testez OpenAI via l'API REST
   - Testez MCP directement
   - Testez Telegram séparément

3. **Redémarrez tout** :
   - Arrêtez tous les serveurs
   - Redémarrez MCP
   - Redémarrez Chatboot

## 💡 Note Importante

Le bot Telegram a maintenant une meilleure gestion d'erreur :
- Si OpenAI échoue, le bot enverra un message d'erreur à l'utilisateur
- Si Telegram échoue, l'application continuera de fonctionner via l'API REST
- Les erreurs sont maintenant loggées sans faire planter l'application


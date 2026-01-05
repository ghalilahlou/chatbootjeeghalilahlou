# Guide de Démarrage - Chatboot avec MCP

## ⚠️ IMPORTANT : Ne pas utiliser IntelliJ directement

**IntelliJ utilise le mauvais classpath** pour le serveur MCP. Utilisez **Maven depuis le terminal PowerShell**.

## 🚀 Démarrage Rapide

### Option 1 : Scripts PowerShell (RECOMMANDÉ)

**Terminal 1 - Serveur MCP :**
```powershell
.\start-mcp-server.ps1
```

**Terminal 2 - Chatboot :**
```powershell
.\start-chatboot.ps1
```

### Option 2 : Maven Manuel

**Terminal 1 - Serveur MCP (port 8989) :**
```powershell
cd mcp-server
mvn clean compile
mvn spring-boot:run
```

**Terminal 2 - Chatboot (port 8087) :**
```powershell
cd C:\Users\ghali\Documents\sonar\chatboot
mvn spring-boot:run
```

## 📋 Ordre de Démarrage

1. **D'abord** : Démarrer le serveur MCP (port 8989)
2. **Ensuite** : Démarrer Chatboot (port 8087)

## ✅ Vérification

Une fois les deux serveurs démarrés :

- **Serveur MCP** : http://localhost:8989/mcp
- **Chatboot** : http://localhost:8087/api/health

## 🔧 Structure du Projet

```
chatboot/                          # Projet principal (client MCP)
├── pom.xml                        # Dépendances : spring-ai-starter-mcp-client
├── src/main/java/org/example/chatboot/
│   └── ChatbootApplication.java   # Application principale
│
└── mcp-server/                    # Service MCP séparé (serveur MCP)
    ├── pom.xml                    # Dépendances : spring-ai-starter-mcp-server-webmvc
    └── src/main/java/org/example/mcpserver/
        ├── McpServerApplication.java
        └── tools/
            └── McpTools.java      # Outils MCP exposés
```

## ❌ Pourquoi IntelliJ ne fonctionne pas ?

IntelliJ détecte `McpServerApplication` mais utilise le classpath de `chatboot` qui contient :
- `spring-ai-starter-mcp-client` (client MCP)
- Les dépendances de chatboot

Le serveur MCP doit utiliser son propre classpath avec uniquement :
- `spring-ai-starter-mcp-server-webmvc` (serveur MCP)
- Pas de dépendances client MCP

## 🛠️ Solution IntelliJ (si vraiment nécessaire)

1. **Ouvrir mcp-server comme projet séparé** :
   - File > Open > `C:\Users\ghali\Documents\sonar\chatboot\mcp-server`
   - IntelliJ détectera le `pom.xml` et configurera le projet

2. **Ou utiliser Maven dans IntelliJ** :
   - View > Tool Windows > Maven
   - Ajouter le projet `mcp-server/pom.xml`
   - Exécuter `mcp-server > Plugins > spring-boot > spring-boot:run`

## 📝 Notes

- Le serveur MCP doit être démarré **avant** Chatboot
- Les deux services sont complètement indépendants
- Utilisez Maven depuis le terminal pour éviter les problèmes de classpath


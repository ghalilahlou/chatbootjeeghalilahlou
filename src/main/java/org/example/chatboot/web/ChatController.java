package org.example.chatboot.web;

import org.example.chatboot.ai.AIAgent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {
    private final AIAgent aiAgent;

    public ChatController(AIAgent aiAgent) {
        this.aiAgent = aiAgent;
    }

    /**
     * Endpoint GET pour tester rapidement via navigateur
     * Exemple: http://localhost:8087/api/chat?query=Bonjour
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chatGet(@RequestParam("query") String query) {
        return aiAgent.askAgent(query);
    }

    /**
     * Endpoint POST pour les requêtes HTTP
     * Exemple: POST http://localhost:8087/api/chat
     * Body: {"query": "Bonjour, comment ça va?"}
     */
    @PostMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    public ChatResponse chatPost(@RequestBody ChatRequest request) {
        String response = aiAgent.askAgent(request.getQuery());
        return new ChatResponse(response);
    }

    /**
     * Endpoint de santé pour vérifier que l'API fonctionne
     */
    @GetMapping("/health")
    public String health() {
        return "Chatbot API is running!";
    }

    // Classes internes pour les requêtes/réponses
    public static class ChatRequest {
        private String query;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }

    public static class ChatResponse {
        private String response;

        public ChatResponse(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}


package org.example.chatboot.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AIAgent {
    private final ChatClient chatClient;

    public AIAgent(ChatClient.Builder builder, ChatMemory memory, ToolCallbackProvider tools) {
        // Log available tool definitions (mirrors reference project behavior)
        System.out.println("=== Outils MCP disponibles ===");
        Arrays.stream(tools.getToolCallbacks()).forEach(toolCallback -> {
            System.out.println("----------------------");
            System.out.println(toolCallback.getToolDefinition());
            System.out.println("----------------------");
        });
        System.out.println("==============================");

        this.chatClient = builder
                .defaultSystem("Vous etes un agent qui se charge de répondre aux questions des users en fonction de contexte fourni. " +
                        "Vous avez accès à des outils MCP pour récupérer des informations sur les employés. " +
                        "Si aucun contexte n'est donné, répond avec 'je ne sais pas'")
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .defaultToolCallbacks(tools)
                .build();
    }

    public String askAgent(String query) {
        try {
            return chatClient.prompt()
                    .user(query)
                    .call()
                    .content();
        } catch (Exception e) {
            // Extraire le message d'erreur plus détaillé
            String errorMessage = e.getMessage();
            Throwable cause = e.getCause();
            
            // Si c'est une erreur OpenAI, essayer d'extraire plus d'infos
            if (errorMessage != null && errorMessage.contains("OpenAiApi")) {
                System.err.println("❌ Erreur OpenAI détectée:");
                System.err.println("   Message: " + errorMessage);
                if (cause != null) {
                    System.err.println("   Cause: " + cause.getMessage());
                }
                return "Désolé, je ne peux pas répondre pour le moment. " +
                       "Vérifiez que votre clé API OpenAI est valide et que vous avez des crédits disponibles.";
            }
            
            // Pour les autres erreurs, propager l'exception
            throw new RuntimeException("Erreur lors de l'appel à l'agent IA: " + errorMessage, e);
        }
    }
}


package org.example.chatboot.telegram;

import jakarta.annotation.PostConstruct;
import org.example.chatboot.ai.AIAgent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.api.key}")
    private String telegramBotToken;

    @Value("${telegram.bot.username:chatboot_helper_bot}")
    private String botUsername;

    private final AIAgent aiAgent;

    public TelegramBot(AIAgent aiAgent) {
        this.aiAgent = aiAgent;
    }

    @PostConstruct
    public void registerTelegramBot() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
            System.out.println("✅ Bot Telegram enregistré avec succès: @" + botUsername);
        } catch (TelegramApiException e) {
            System.err.println("❌ Erreur lors de l'enregistrement du bot Telegram: " + e.getMessage());
            System.err.println("⚠️  Le bot Telegram ne sera pas disponible. Vérifiez:");
            System.err.println("   1. La clé API Telegram est valide");
            System.err.println("   2. La connexion Internet est active");
            System.err.println("   3. Le nom d'utilisateur du bot est correct");
            // Ne pas faire échouer l'application si Telegram ne fonctionne pas
            // L'application peut toujours fonctionner via l'API REST
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || update.getMessage().getText() == null) {
            return;
        }
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        
        try {
            String answer = aiAgent.askAgent(messageText);
            sendTextMessage(chatId, answer);
        } catch (Exception e) {
            // Gérer les erreurs gracieusement
            String errorMessage = "Désolé, une erreur s'est produite lors du traitement de votre message. " +
                    "Veuillez réessayer plus tard.";
            
            // Si l'erreur vient d'OpenAI, utiliser un message plus spécifique
            String errorMsg = e.getMessage();
            if (errorMsg != null && (errorMsg.contains("OpenAI") || errorMsg.contains("OpenAiApi") || 
                errorMsg.contains("text/plain"))) {
                errorMessage = "Désolé, je ne peux pas répondre pour le moment. " +
                              "Vérifiez que votre clé API OpenAI est valide et que vous avez des crédits disponibles.";
            }
            
            try {
                sendTextMessage(chatId, errorMessage);
            } catch (Exception ex) {
                System.err.println("Erreur lors de l'envoi du message d'erreur: " + ex.getMessage());
            }
            System.err.println("Erreur lors du traitement du message Telegram: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return telegramBotToken;
    }

    private void sendTextMessage(long chatId, String text) {
        try {
            SendMessage message = new SendMessage(String.valueOf(chatId), text);
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Erreur lors de l'envoi du message Telegram: " + e.getMessage());
            // Ne pas faire échouer l'application, juste logger l'erreur
        }
    }
}


package valeriatolstova.telegram_bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import static org.graalvm.compiler.nodes.java.RegisterFinalizerNode.register;

public class Main extends TelegramLongPollingBot {

    public static final String USERNAME = "NameOfBot_bot";
    public static final String TOKEN = "915040047:AAGeoRStxsnIdyU2srwM9zlk9fmlPYevlXo";

    public Main(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage;
        switch (message.getText()) {
             case "/start":
                sendMessage = new SendMessage(message.getChatId(), "You can write: Hello; How are you?; Too");
              break;
            case "Hello":
                sendMessage = new SendMessage(message.getChatId(),
                        "Hello, " + update.getMessage().getFrom().getFirstName());
                break;
            case "How are you?":
                sendMessage = new SendMessage(message.getChatId(), "I'm fine. And you?");
                break;
            case "Too":
                sendMessage=new SendMessage(message.getChatId(), "Good luck");
                break;
            default:
                sendMessage = new SendMessage(message.getChatId(), message.getText());
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
            botOptions.setProxyHost("177.8.226.254");
            botOptions.setProxyPort(8080);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
            telegramBotsApi.registerBot((LongPollingBot) new Main(botOptions));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
   }





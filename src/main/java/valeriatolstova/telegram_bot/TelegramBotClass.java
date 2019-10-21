package valeriatolstova.telegram_bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramBotClass extends TelegramLongPollingCommandBot {
    private static final String BOT_USERNAME = "NameOfBot_bot";
    private static final String BOT_TOKEN = "915040047:AAGeoRStxsnIdyU2srwM9zlk9fmlPYevlXo";

    TelegramBotClass(DefaultBotOptions botOptions) {
        super(botOptions,BOT_USERNAME);
        register(new FirstCommand());
        register(new InlineKeyboardMy());
        register(new ReplyKeyboardMy());
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
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
                sendMessage = new SendMessage(message.getChatId(), "Good luck");
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
}
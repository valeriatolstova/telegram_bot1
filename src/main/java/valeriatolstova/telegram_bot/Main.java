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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.util.ArrayList;
import java.util.List;

import static org.graalvm.compiler.nodes.java.RegisterFinalizerNode.register;

public class Main extends TelegramLongPollingCommandBot {

    public static final String USERNAME = "NameOfBot_bot";
    public static final String TOKEN = "915040047:AAGeoRStxsnIdyU2srwM9zlk9fmlPYevlXo";

    public Main(DefaultBotOptions botOptions) {
        super(String.valueOf(botOptions));
        register(new HelloCommand());
    }

 /*   @Override
    public String getBotUsername() {
        return USERNAME;
    }*/

    @Override
    public String getBotToken() {
        return TOKEN;
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

    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = chat.getUserName();
        if (userName == null || userName.isEmpty()) {
            userName = user.getFirstName() + "" + user.getLastName();
        }
        StringBuilder messageTextBuilder = new StringBuilder("Hello").append(userName);
        if (arguments != null && arguments.length > 0) {
            messageTextBuilder.append("\n");
            messageTextBuilder.append("Thank you so much for your kind words:\n");
            messageTextBuilder.append(String.join("", arguments));
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageTextBuilder.toString());
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboard getKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("echo");
        keyboardFirstRow.add("double echo");
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("Hello!");
        keyboardSecondRow.add('\u23F0' + "Hello!" + (System.currentTimeMillis() / 1000));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private ReplyKeyboard getInlineKeyboardMarkup() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keyboardFirstRow = new ArrayList<>();

        keyboardFirstRow.add(new InlineKeyboardButton("only text").setSwitchInlineQueryCurrentChat("text"));
        keyboardFirstRow.add(new InlineKeyboardButton("ok.ru").setCallbackData("ok"));

        List<InlineKeyboardButton> keyboardSecondRow = new ArrayList<>();
        keyboardSecondRow.add(new InlineKeyboardButton("share").setSwitchInlineQuery("It's a super bot"));
        keyboardSecondRow.add(new InlineKeyboardButton("next").setCallbackData("next"));
        keyboard.add(keyboardSecondRow);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}





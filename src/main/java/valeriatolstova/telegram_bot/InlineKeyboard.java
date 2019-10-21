package valeriatolstova.telegram_bot;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class InlineKeyboard implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

    }
}

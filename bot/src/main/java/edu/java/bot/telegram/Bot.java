package edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.CommandKeeper;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.dao.LinkDaoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bot {

    private final TelegramBot bot;
    private final ApplicationConfig config;
    private final LinkDaoInterface linkDao;
    private final CommandKeeper commandKeeper;

    private static final String START = "/start";
    private static final String HELP = "/help";
    private static final String TRACK = "/track";
    private static final String UNTRACK = "/untrack";
    private static final String LIST = "/list";

    @Autowired
    public Bot(ApplicationConfig config, LinkDaoInterface linkDao, TelegramBot bot, CommandKeeper commandKeeper) {
        this.config = config;
        this.linkDao = linkDao;
        this.bot = bot;
        this.commandKeeper = commandKeeper;
        setUpBot();
        bot.execute(new SetMyCommands(commandKeeper.getMenuCommands()));
    }

    private void setUpBot() {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message() != null) {
                    handleUpdate(update);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void handleUpdate(Update update) {
        String text = update.message().text();
        long id = update.message().from().id();

        if (text.startsWith("/")) {
            executeCommand(id, text);
        } else {
            bot.execute(new SendMessage(id, "нужно использовать одну из существующих команд"));
        }
    }

    private void executeCommand(Long chatId, String message) {
        String[] parts = message.split(" ");

        switch (parts[0]) {
            case START:
                commandKeeper.get(START).handle(chatId);
                break;
            case HELP:
                commandKeeper.get(HELP).handle(chatId);
                break;
            case TRACK:
                commandKeeper.get(TRACK).handle(chatId, parts.length > 1 ? parts[1] : "");
                break;
            case UNTRACK:
                commandKeeper.get(UNTRACK).handle(chatId, parts.length > 1 ? parts[1] : "");
                break;
            case LIST:
                commandKeeper.get(LIST).handle(chatId);
                break;
            default:
                bot.execute(new SendMessage(chatId, "команда не существует"));
                break;
        }
    }
}

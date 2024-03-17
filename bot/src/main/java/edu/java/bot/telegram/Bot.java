package edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.CommandKeeper;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bot {

    private final TelegramBot bot;
    private final ApplicationConfig config;
    private final CommandKeeper commandKeeper;

    @Autowired
    public Bot(ApplicationConfig config, TelegramBot bot, CommandKeeper commandKeeper) {
        this.config = config;
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
            executeCommand(update);
        } else {
            bot.execute(new SendMessage(id, "нужно использовать одну из существующих команд"));
        }
    }

    private void executeCommand(Update update) {
        String[] parts = update.message().text().split(" ");

        if (commandKeeper.get(parts[0]) != null) {
            bot.execute(commandKeeper.get(parts[0]).handle(update));
        } else {
            bot.execute(new SendMessage(update.message().from().id(), "команда не существует"));
        }
    }
}

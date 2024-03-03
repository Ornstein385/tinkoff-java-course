package edu.java.bot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dao.LinkDaoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends Command {

    public HelpCommand(TelegramBot bot, LinkDaoInterface linkDao) {
        super(bot, linkDao);
    }

    @Autowired
    @Lazy
    public void setCommandKeeper(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
    }

    public CommandKeeper commandKeeper;

    @Override
    public String getCommand() {
        return "/help";
    }

    @Override
    public String getDescription() {
        return "посмотреть описание всех команд";
    }

    @Override
    public void handle(long id, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (Command command : commandKeeper.getAll()) {
            sb.append(command.getCommand()).append(" -> ").append(command.getDescription()).append("\n");
        }
        bot.execute(new SendMessage(id, sb.toString()));
    }
}

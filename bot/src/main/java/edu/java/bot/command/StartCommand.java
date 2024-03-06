package edu.java.bot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dao.LinkDaoInterface;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends Command {

    public StartCommand(TelegramBot bot, LinkDaoInterface linkDao) {
        super(bot, linkDao);
    }

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "регистрация";
    }

    @Override
    public void handle(long id, String[] args) {
        bot.execute(new SendMessage(id, "вы зарегистрированы"));
    }
}

package edu.java.bot.command;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.dao.LinkDaoInterface;

public abstract class Command {

    public Command(TelegramBot bot, LinkDaoInterface linkDao) {
        this.bot = bot;
        this.linkDao = linkDao;
    }

    public abstract String getCommand();

    public abstract String getDescription();

    protected final TelegramBot bot;
    protected final LinkDaoInterface linkDao;

    public abstract void handle(long id, String... args);
}

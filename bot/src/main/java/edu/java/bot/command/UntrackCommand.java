package edu.java.bot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dao.LinkDaoInterface;
import edu.java.bot.models.Link;
import edu.java.bot.telegram.AllowedDomainChecker;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand extends Command {

    protected UntrackCommand(TelegramBot bot, LinkDaoInterface linkDao) {
        super(bot, linkDao);
    }

    @Override
    public String getCommand() {
        return "/untrack";
    }

    @Override
    public String getDescription() {
        return "прекратить отслеживание ресурса";
    }

    @Override
    public void handle(long id, String[] args) {
        Link link = new Link(id, args[0]);
        if (AllowedDomainChecker.isAllowed(link)) {
            linkDao.remove(link);
        } else {
            bot.execute(new SendMessage(id, "ресурс пока не поддерживается"));
        }
    }
}

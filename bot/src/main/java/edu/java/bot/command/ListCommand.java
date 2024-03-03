package edu.java.bot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dao.LinkDaoInterface;
import edu.java.bot.models.Link;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends Command {

    public ListCommand(TelegramBot bot, LinkDaoInterface linkDao) {
        super(bot, linkDao);
    }

    @Override
    public String getCommand() {
        return "/list";
    }

    @Override
    public String getDescription() {
        return "показать все отслеживаемые ссылки";
    }

    @Override
    public void handle(long id, String[] args) {
        if (linkDao.getSize(id) == 0) {
            bot.execute(new SendMessage(id, "нет отслеживаемых ссылок"));
            return;
        }

        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Link link : linkDao.getAll(id)) {
            sb.append(i).append(".\n");
            sb.append(link.getUrl()).append("\n\n");
            i++;
        }
        bot.execute(new SendMessage(id, sb.toString()));
    }
}

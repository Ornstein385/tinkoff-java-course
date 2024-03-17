package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dao.LinkDaoInterface;
import edu.java.bot.models.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {

    @Autowired
    public void setLinkDao(LinkDaoInterface linkDao) {
        this.linkDao = linkDao;
    }

    public LinkDaoInterface linkDao;

    @Override
    public String getCommand() {
        return "/list";
    }

    @Override
    public String getDescription() {
        return "показать все отслеживаемые ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        long id = update.message().from().id();
        if (linkDao.getSize(id) == 0) {
            return new SendMessage(id, "нет отслеживаемых ссылок");
        }

        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Link link : linkDao.getAll(id)) {
            sb.append(i).append(".\n");
            sb.append(link.getUrl()).append("\n\n");
            i++;
        }
        return new SendMessage(id, sb.toString());
    }
}

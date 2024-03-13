package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dao.LinkDaoInterface;
import edu.java.bot.models.Link;
import edu.java.bot.telegram.AllowedDomainChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {

    @Autowired
    public void setLinkDao(LinkDaoInterface linkDao) {
        this.linkDao = linkDao;
    }

    public LinkDaoInterface linkDao;

    @Override
    public String getCommand() {
        return "/track";
    }

    @Override
    public String getDescription() {
        return "начать отслеживание ресурса";
    }

    @Override
    public SendMessage handle(Update update) {
        long id = update.message().from().id();
        String[] parts = update.message().text().split(" ");
        if (parts.length < 2) {
            return new SendMessage(id, "некорректный формат команды");
        }
        Link link = new Link(id, parts[1]);
        if (AllowedDomainChecker.isAllowed(link)) {
            linkDao.add(link);
            return new SendMessage(id, "ссылка добавлена для отслеживания");
        } else {
            return new SendMessage(id, "ресурс пока не поддерживается");
        }
    }
}

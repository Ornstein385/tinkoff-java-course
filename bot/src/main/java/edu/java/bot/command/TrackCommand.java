package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.api.request.AddLinkRequest;
import edu.java.bot.telegram.LinkTypeDeterminant;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {

    @Autowired
    public TrackCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    private ScrapperClient scrapperClient;

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
        URI url = URI.create(parts[1]);
        if (LinkTypeDeterminant.isCorrectLink(url)) {
            scrapperClient.addLink(id, new AddLinkRequest(url));
            return new SendMessage(id, "ссылка добавлена для отслеживания");
        } else {
            return new SendMessage(id, "ресурс пока не поддерживается");
        }
    }
}

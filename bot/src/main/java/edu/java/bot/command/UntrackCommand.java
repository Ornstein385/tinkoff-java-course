package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.api.request.RemoveLinkRequest;
import edu.java.bot.telegram.LinkTypeDeterminant;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {

    @Autowired
    public UntrackCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    private ScrapperClient scrapperClient;

    @Override
    public String getCommand() {
        return "/untrack";
    }

    @Override
    public String getDescription() {
        return "прекратить отслеживание ресурса";
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
            scrapperClient.removeLink(id, new RemoveLinkRequest(url));
            return new SendMessage(id, "ссылка удалена из отслеживания");
        } else {
            return new SendMessage(id, "ресурс пока не поддерживается");
        }
    }
}

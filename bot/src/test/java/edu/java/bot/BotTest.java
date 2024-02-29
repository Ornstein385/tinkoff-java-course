package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.controllers.Bot;
import edu.java.bot.dao.LinkDaoInterface;
import edu.java.bot.dao.LinkTemporaryDaoImpl;
import edu.java.bot.models.Link;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BotTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private ApplicationConfig config;

    @Spy
    private LinkDaoInterface linkDao;

    @InjectMocks
    private Bot bot;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(config.getTelegramToken()).thenReturn("dummy_token");
        bot = new Bot(config, linkDao, telegramBot);
    }

    @Test
    public void testTrackSupportedUrl() {
        String supportedUrl = "https://github.com/example";
        long chatId = 123456L;

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/track " + supportedUrl);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(chatId);

        bot.handleUpdate(update);

        verify(linkDao, times(1)).add(any(Link.class));
    }

    @Test
    public void testTrackUnsupportedUrl() {
        String unsupportedUrl = "https://unsupported.com/example";
        long chatId = 123456L;

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/track " + unsupportedUrl);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(chatId);

        bot.handleUpdate(update);

        verify(linkDao, never()).add(any(Link.class));
    }

    @Test
    public void testDoubleAdditionOfLink() {
        String url = "https://github.com/doubleAddTest";
        long chatId = 123456L;

        linkDao = new LinkTemporaryDaoImpl();

        Update update1 = mock(Update.class);
        Update update2 = mock(Update.class);

        Message message1 = mock(Message.class);
        User user1 = mock(User.class);
        Message message2 = mock(Message.class);
        User user2 = mock(User.class);

        Link link = new Link(chatId, url);

        when(update1.message()).thenReturn(message1);
        when(update2.message()).thenReturn(message2);
        when(message1.from()).thenReturn(user1);
        when(user1.id()).thenReturn(chatId);
        when(message2.from()).thenReturn(user2);
        when(user2.id()).thenReturn(chatId);

        when(update1.message().text()).thenReturn("/track " + url);
        when(update1.message().from().id()).thenReturn(chatId);
        when(update2.message().text()).thenReturn("/track " + url);
        when(update2.message().from().id()).thenReturn(chatId);

        bot = new Bot(config, linkDao, telegramBot);

        bot.handleUpdate(update1);
        bot.handleUpdate(update2);

        Collection<Link> links = linkDao.getAll(chatId);
        assertEquals(1, links.size());
        assertTrue(links.contains(link));
    }

    @Test
    public void testDoubleRemovalOfLink() {
        String url = "https://github.com/doubleRemoveTest";
        long chatId = 123456L;

        linkDao = new LinkTemporaryDaoImpl();
        Link link = new Link(chatId, url);
        linkDao.add(link);

        Update update1 = mock(Update.class);
        Update update2 = mock(Update.class);

        Message message1 = mock(Message.class);
        User user1 = mock(User.class);
        Message message2 = mock(Message.class);
        User user2 = mock(User.class);

        when(update1.message()).thenReturn(message1);
        when(update2.message()).thenReturn(message2);
        when(message1.from()).thenReturn(user1);
        when(user1.id()).thenReturn(chatId);
        when(message2.from()).thenReturn(user2);
        when(user2.id()).thenReturn(chatId);

        when(update1.message().text()).thenReturn("/untrack " + url);
        when(update1.message().from().id()).thenReturn(chatId);
        when(update2.message().text()).thenReturn("/untrack " + url);
        when(update2.message().from().id()).thenReturn(chatId);

        bot = new Bot(config, linkDao, telegramBot);

        bot.handleUpdate(update1);
        bot.handleUpdate(update2);

        Collection<Link> links = linkDao.getAll(chatId);
        assertTrue(links.isEmpty());
    }

    @Test
    public void testExecutionOfNonexistentCommand() {
        long chatId = 123456L;
        String nonExistentCommand = "/nonexistent";

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(nonExistentCommand);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(chatId);

        bot.handleUpdate(update);

        ArgumentCaptor<SendMessage> sendMessageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(sendMessageCaptor.capture());

        SendMessage sentRequest = sendMessageCaptor.getValue();

        assertEquals(chatId, sentRequest.getParameters().get("chat_id"));
        assertTrue(sentRequest.getParameters().get("text").toString().contains("команда не существует"));
    }

    @Test
    public void testExecutionOfNonCommand() {
        long chatId = 123456L;
        String someText = "someText";

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(someText);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(chatId);

        bot.handleUpdate(update);

        ArgumentCaptor<SendMessage> sendMessageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(sendMessageCaptor.capture());

        SendMessage sentRequest = sendMessageCaptor.getValue();

        assertEquals(chatId, sentRequest.getParameters().get("chat_id"));
        assertTrue(sentRequest.getParameters().get("text").toString().contains("нужно использовать одну из существующих команд"));
    }

    @Test
    public void testEmptyTrackListMessage() {
        long chatId = 123456L;
        String listCommand = "/list";

        linkDao = new LinkTemporaryDaoImpl();

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(listCommand);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(chatId);

        bot.handleUpdate(update);

        ArgumentCaptor<SendMessage> sendMessageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(sendMessageCaptor.capture());

        SendMessage sentRequest = sendMessageCaptor.getValue();

        assertEquals(chatId, sentRequest.getParameters().get("chat_id"));
        assertTrue(sentRequest.getParameters().get("text").toString().contains("нет отслеживаемых ссылок"));
    }

}

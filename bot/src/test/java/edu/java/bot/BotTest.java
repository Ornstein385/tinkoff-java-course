package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.CommandKeeper;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.TrackCommand;
import edu.java.bot.command.UntrackCommand;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.dao.LinkDaoInterface;
import edu.java.bot.models.Link;
import edu.java.bot.telegram.Bot;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BotTest {

    @Mock
    private TelegramBot bot;
    @Mock
    private ApplicationConfig config;
    @Spy
    private LinkDaoInterface linkDao;
    @Spy
    private CommandKeeper commandKeeper;

    @InjectMocks
    private Bot testBot;

    @Captor
    private ArgumentCaptor<SendMessage> sendMessageCaptor;

    @BeforeEach
    public void setupBeforeEach() {
        MockitoAnnotations.openMocks(this);

        StartCommand startCommand = new StartCommand();
        TrackCommand trackCommand = new TrackCommand();
        UntrackCommand untrackCommand = new UntrackCommand();
        ListCommand listCommand = new ListCommand();
        HelpCommand helpCommand = new HelpCommand();
        commandKeeper.setupCommands(Arrays.asList(
            startCommand,
            trackCommand,
            untrackCommand,
            listCommand,
            helpCommand
        ));

        trackCommand.setLinkDao(linkDao);
        untrackCommand.setLinkDao(linkDao);
        listCommand.setLinkDao(linkDao);
        helpCommand.setCommands(Arrays.stream(commandKeeper.getAll()).toList());
    }

    private void simulateUpdate(String text, long userId) {
        Update mockUpdate = mock(Update.class);
        when(mockUpdate.message()).thenReturn(mock(com.pengrad.telegrambot.model.Message.class));
        when(mockUpdate.message().text()).thenReturn(text);
        when(mockUpdate.message().from()).thenReturn(mock(com.pengrad.telegrambot.model.User.class));
        when(mockUpdate.message().from().id()).thenReturn(userId);

        testBot.handleUpdate(mockUpdate);
    }

    @Test
    public void testStartCommand() {
        simulateUpdate("/start", 123L);
        verify(bot).execute(sendMessageCaptor.capture());
        assertEquals("вы зарегистрированы", sendMessageCaptor.getValue().getParameters().get("text"));
    }

    @Test
    public void testHelpCommand() {
        simulateUpdate("/help", 123L);
        verify(bot).execute(sendMessageCaptor.capture());
        assertNotEquals("", sendMessageCaptor.getValue().getParameters().get("text"));
    }

    @Test
    public void testTrackCommandWithValidDomain() {
        simulateUpdate("/track https://github.com/user/repo", 123L);
        verify(linkDao).add(any());
    }

    @Test
    public void testTrackCommandWithInvalidDomain() {
        simulateUpdate("/track https://example.com/page", 123L);
        verify(bot).execute(sendMessageCaptor.capture());
        assertEquals("ресурс пока не поддерживается", sendMessageCaptor.getValue().getParameters().get("text"));
    }

    @Test
    public void testUntrackCommandWithExistingLink() {
        simulateUpdate("/untrack https://github.com/user/repo", 123L);
        verify(linkDao).remove(any());
    }

    @Test
    public void testListCommandWithNoLinks() {
        when(linkDao.getSize(anyLong())).thenReturn(0);
        simulateUpdate("/list", 123L);
        verify(bot).execute(sendMessageCaptor.capture());
        assertEquals("нет отслеживаемых ссылок", sendMessageCaptor.getValue().getParameters().get("text"));
    }

    @Test
    public void testListCommandWithLinks() {
        when(linkDao.getSize(anyLong())).thenReturn(1);
        when(linkDao.getAll(anyLong())).thenReturn(List.of(new Link(123L, "https://github.com/user/repo")));
        simulateUpdate("/list", 123L);
        verify(bot).execute(sendMessageCaptor.capture());
        String messageText = (String) sendMessageCaptor.getValue().getParameters().get("text");
        assertTrue(messageText.contains("https://github.com/user/repo"));
    }

    @Test
    public void testUnknownCommand() {
        simulateUpdate("/unknown", 123L);
        verify(bot).execute(sendMessageCaptor.capture());
        assertEquals("команда не существует", sendMessageCaptor.getValue().getParameters().get("text"));
    }

    @Test
    public void testNonCommandMessage() {
        simulateUpdate("SomeText", 123L);
        verify(bot).execute(sendMessageCaptor.capture());
        assertEquals(
            "нужно использовать одну из существующих команд",
            sendMessageCaptor.getValue().getParameters().get("text")
        );
    }
}

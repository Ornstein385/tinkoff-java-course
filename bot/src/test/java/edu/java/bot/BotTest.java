package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.CommandKeeper;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.TrackCommand;
import edu.java.bot.command.UntrackCommand;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.dto.api.request.AddLinkRequest;
import edu.java.bot.dto.api.request.RemoveLinkRequest;
import edu.java.bot.dto.api.response.LinkResponse;
import edu.java.bot.dto.api.response.ListLinksResponse;
import edu.java.bot.telegram.Bot;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BotTest {

    @Mock
    private TelegramBot bot;
    @Mock
    private ApplicationConfig config;
    @Mock
    private ScrapperClient scrapperClient;
    @Spy
    private CommandKeeper commandKeeper;

    @InjectMocks
    private Bot testBot;

    @Captor
    private ArgumentCaptor<SendMessage> sendMessageCaptor;

    @BeforeEach
    public void setupBeforeEach() {
        MockitoAnnotations.openMocks(this);

        StartCommand startCommand = new StartCommand(scrapperClient);
        TrackCommand trackCommand = new TrackCommand(scrapperClient);
        UntrackCommand untrackCommand = new UntrackCommand(scrapperClient);
        ListCommand listCommand = new ListCommand(scrapperClient);
        HelpCommand helpCommand = new HelpCommand();
        commandKeeper.setupCommands(Arrays.asList(
            startCommand,
            trackCommand,
            untrackCommand,
            listCommand,
            helpCommand
        ));

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

    private void simulateAddLink(long userId, URI url) {
        AddLinkRequest addLinkRequest = new AddLinkRequest(url);
        LinkResponse linkResponse = new LinkResponse(userId, url);
        when(scrapperClient.addLink(userId, addLinkRequest)).thenReturn(linkResponse);
    }

    private void simulateRemoveLink(long userId, URI url) {
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(url);
        when(scrapperClient.removeLink(userId, removeLinkRequest)).thenReturn("200");
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
        simulateAddLink(123L, URI.create("https://github.com/user/repo"));
        verify(scrapperClient).addLink(123L, new AddLinkRequest(URI.create("https://github.com/user/repo")));
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
        simulateRemoveLink(123L, URI.create("https://github.com/user/repo"));
        verify(scrapperClient).removeLink(123L, new RemoveLinkRequest(URI.create("https://github.com/user/repo")));
    }

    @Test
    public void testListCommandWithNoLinks() {
        when(scrapperClient.getAllLinks(any())).thenReturn(new ListLinksResponse(Collections.emptyList(), 0));
        simulateUpdate("/list", 123L);
        verify(bot).execute(sendMessageCaptor.capture());
        assertEquals("нет отслеживаемых ссылок", sendMessageCaptor.getValue().getParameters().get("text"));
    }

    @Test
    public void testListCommandWithLinks() {
        when(scrapperClient.getAllLinks(any())).thenReturn(new ListLinksResponse(Collections.singletonList(new LinkResponse(
            123L,
            URI.create("https://github.com/user/repo")
        )), 1));
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

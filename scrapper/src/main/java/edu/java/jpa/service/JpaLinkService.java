package edu.java.jpa.service;

import edu.java.jpa.model.ChatEntity;
import edu.java.jpa.model.LinkChatEntity;
import edu.java.jpa.model.LinkEntity;
import edu.java.jpa.repository.JpaChatRepository;
import edu.java.jpa.repository.JpaLinkChatRepository;
import edu.java.jpa.repository.JpaLinkRepository;
import edu.java.model.Link;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;
    private final JpaLinkChatRepository linkChatRepository;

    public JpaLinkService(
        JpaLinkRepository linkRepository,
        JpaChatRepository chatRepository,
        JpaLinkChatRepository linkChatRepository
    ) {
        this.linkRepository = linkRepository;
        this.chatRepository = chatRepository;
        this.linkChatRepository = linkChatRepository;
    }

    @Override
    @Transactional
    public void add(long tgChatId) {
        ChatEntity chat = new ChatEntity(tgChatId, OffsetDateTime.now());
        chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void add(long tgChatId, URI url) {
        ChatEntity chat = chatRepository.findById(tgChatId).orElse(new ChatEntity(tgChatId, OffsetDateTime.now()));
        chatRepository.save(chat);

        LinkEntity link =
            linkRepository.findByUrl(url.toString()).orElse(new LinkEntity(null, url.toString(), OffsetDateTime.now()));
        linkRepository.save(link);

        LinkChatEntity linkChat = new LinkChatEntity(link, chat, OffsetDateTime.now());
        linkChatRepository.save(linkChat);
    }

    @Override
    @Transactional
    public void remove(long tgChatId) {
        chatRepository.deleteById(tgChatId);
    }

    @Override
    @Transactional
    public void remove(long tgChatId, URI url) {
        linkChatRepository.deleteByChatIdAndLinkUrl(tgChatId, url.toString());
    }

    @Override
    public Collection<Link> listAllLinksForChat(long tgChatId) {
        return linkChatRepository.findLinksByChatId(tgChatId).stream()
            .map(linkEntity -> new Link(URI.create(linkEntity.getUrl()), linkEntity.getLastUpdated()))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Link> listAllLinksForChat(long tgChatId, long millisecondsBack, int limit) {
        OffsetDateTime cutoffTime = OffsetDateTime.now().minus(millisecondsBack, ChronoUnit.MILLIS);
        return linkChatRepository.findLinksForChatBefore(tgChatId, millisecondsBack, PageRequest.of(0, limit)).stream()
            .map(linkEntity -> new Link(URI.create(linkEntity.getUrl()), linkEntity.getLastUpdated()))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Link> listAllLinks() {
        return linkRepository.findAll().stream()
            .map(linkEntity -> new Link(URI.create(linkEntity.getUrl()), linkEntity.getLastUpdated()))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Link> listAllLinks(long millisecondsBack, int limit) {
        OffsetDateTime cutoffTime = OffsetDateTime.now().minus(millisecondsBack, ChronoUnit.MILLIS);
        return linkRepository.findAllWithLastUpdatedBefore(cutoffTime, PageRequest.of(0, limit)).stream()
            .map(linkEntity -> new Link(URI.create(linkEntity.getUrl()), linkEntity.getLastUpdated()))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Long> listAllChatsForLink(URI url) {
        return linkChatRepository.findChatsByLinkUrl(url.toString()).stream()
            .map(ChatEntity::getId)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Long> listAllChatsForLink(URI url, long millisecondsBack, int limit) {
        OffsetDateTime cutoffTime = OffsetDateTime.now().minus(millisecondsBack, ChronoUnit.MILLIS);
        return linkChatRepository.findChatsForLinkAfter(url.toString(), millisecondsBack, PageRequest.of(0, limit))
            .stream()
            .map(ChatEntity::getId)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void refreshLinkUpdateTime(URI url) {
        LinkEntity link = linkRepository.findByUrl(url.toString())
            .orElseThrow(() -> new NoSuchElementException("Link not found for update: " + url));
        link.setLastUpdated(OffsetDateTime.now());
        linkRepository.save(link);
    }
}



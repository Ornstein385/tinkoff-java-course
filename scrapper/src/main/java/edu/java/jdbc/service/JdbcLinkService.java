package edu.java.jdbc.service;

import edu.java.jdbc.dao.JdbcChatDao;
import edu.java.jdbc.dao.JdbcLinkChatDao;
import edu.java.jdbc.dao.JdbcLinkDao;
import edu.java.jdbc.dto.ChatDto;
import edu.java.jdbc.dto.LinkChatDto;
import edu.java.jdbc.dto.LinkDto;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    JdbcLinkDao linkDao;
    JdbcChatDao chatDao;
    JdbcLinkChatDao linkChatDao;

    @Autowired
    public JdbcLinkService(JdbcLinkDao linkDao, JdbcChatDao chatDao, JdbcLinkChatDao linkChatDao) {
        this.linkDao = linkDao;
        this.chatDao = chatDao;
        this.linkChatDao = linkChatDao;
    }

    @Override
    public void add(long tgChatId) {
        chatDao.addChat(new ChatDto(tgChatId, null));
    }

    @Override
    public void add(long tgChatId, URI url) {
        chatDao.addChat(new ChatDto(tgChatId, null));
        linkDao.addLink(new LinkDto(null, url.toString(), OffsetDateTime.now()));
        Long linkId = linkDao.getLink(url.toString())
            .orElseThrow(() -> new NoSuchElementException("не был найден URL при добавлении: " + url))
            .getId();
        linkChatDao.addLinkChat(new LinkChatDto(linkId, tgChatId, null));
    }

    @Override
    public void remove(long tgChatId) {
        chatDao.removeChat(tgChatId);
    }

    @Override
    public void remove(long tgChatId, URI url) {
        Long linkId = linkDao.getLink(url.toString())
            .orElseThrow(() -> new NoSuchElementException("не был найден URL при удалении: " + url))
            .getId();
        linkChatDao.removeLinkChat(linkId, tgChatId);
    }

    @Override
    public Collection<URI> listAll(long tgChatId) {
        List<LinkDto> list = linkChatDao.findAllLinksForChat(tgChatId);
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream().map((x) -> URI.create(x.getUrl())).collect(Collectors.toList());
    }

    @Override
    public Collection<URI> listAll(long tgChatId, long millisecondsBack, int limit) {
        List<LinkDto> list = linkChatDao.findAllLinksForChat(tgChatId, millisecondsBack, limit);
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream().map((x) -> URI.create(x.getUrl())).collect(Collectors.toList());
    }
}

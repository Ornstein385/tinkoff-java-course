package edu.java.controller;

import edu.java.dto.api.internal.request.AddLinkRequest;
import edu.java.dto.api.internal.request.RemoveLinkRequest;
import edu.java.dto.api.internal.response.LinkResponse;
import edu.java.dto.api.internal.response.ListLinksResponse;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
public class LinksController {

    LinkService linkService;

    @Autowired
    public LinksController(@Qualifier("jdbcLinkService") LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        List<URI> uriList = (List<URI>) linkService.listAll(tgChatId);

        AtomicLong idGenerator = new AtomicLong(1);

        List<LinkResponse> linkResponses = uriList.stream()
            .map(uri -> {
                LinkResponse linkResponse = new LinkResponse();
                linkResponse.setId(idGenerator.getAndIncrement());
                linkResponse.setUrl(uri);
                return linkResponse;
            })
            .toList();

        ListLinksResponse listLinksResponse = new ListLinksResponse();
        listLinksResponse.setLinks(linkResponses);
        listLinksResponse.setSize(linkResponses.size());

        return ResponseEntity.ok(listLinksResponse);
    }

    @PostMapping
    public ResponseEntity<?> addLink(
        @RequestHeader("Tg-Chat-Id") Long tgChatId,
        @RequestBody AddLinkRequest addLinkRequest
    ) {
        linkService.add(tgChatId, addLinkRequest.getLink());

        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setUrl(addLinkRequest.getLink());
        linkResponse.setId(1L);

        return ResponseEntity.ok(linkResponse);
    }

    @DeleteMapping
    public ResponseEntity<?> removeLink(
        @RequestHeader("Tg-Chat-Id") Long tgChatId,
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) {
        linkService.remove(tgChatId, removeLinkRequest.getLink());

        return ResponseEntity.ok("Ссылка успешно убрана");
    }
}

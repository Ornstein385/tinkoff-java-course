package edu.java.controller;

import edu.java.dto.api.request.AddLinkRequest;
import edu.java.dto.api.request.RemoveLinkRequest;
import edu.java.dto.api.response.LinkResponse;
import edu.java.dto.api.response.ListLinksResponse;
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

    @GetMapping
    public ResponseEntity<?> getAllLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        return ResponseEntity.ok(new ListLinksResponse());
    }

    @PostMapping
    public ResponseEntity<?> addLink(
        @RequestHeader("Tg-Chat-Id") Long tgChatId,
        @RequestBody AddLinkRequest addLinkRequest
    ) {
        return ResponseEntity.ok(new LinkResponse());
    }

    @DeleteMapping
    public ResponseEntity<?> removeLink(
        @RequestHeader("Tg-Chat-Id") Long tgChatId,
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) {
        return ResponseEntity.ok("Ссылка успешно убрана");
    }
}

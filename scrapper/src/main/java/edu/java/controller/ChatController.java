package edu.java.controller;

import edu.java.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
public class ChatController {
    LinkService linkService;

    @Autowired
    public ChatController(@Qualifier("jdbcLinkService") LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> registerChat(@PathVariable("id") Long id) {
        linkService.add(id);
        return ResponseEntity.ok("Чат зарегистрирован");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable("id") Long id) {
        linkService.remove(id);
        return ResponseEntity.ok("Чат успешно удалён");
    }
}

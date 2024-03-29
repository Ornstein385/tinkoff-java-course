package edu.java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
public class ChatController {

    @PostMapping("/{id}")
    public ResponseEntity<?> registerChat(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Чат зарегистрирован");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Чат успешно удалён");
    }
}

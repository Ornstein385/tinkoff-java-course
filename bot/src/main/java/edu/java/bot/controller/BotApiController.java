package edu.java.bot.controller;

import edu.java.bot.dto.api.request.LinkUpdateRequest;
import edu.java.bot.dto.api.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
public class BotApiController {

    @PostMapping
    public ResponseEntity<?> sendUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        try {
            return ResponseEntity.ok().body("Обновление обработано");
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse();
            errorResponse.setDescription("Некорректные параметры запроса");
            errorResponse.setCode("400");
            errorResponse.setExceptionName(e.getClass().getSimpleName());
            errorResponse.setExceptionMessage(e.getMessage());
            // errorResponse.setStacktrace(); // Здесь можно заполнить стек вызовов, если необходимо

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}

package edu.java.controller;

import edu.java.dto.api.response.ApiErrorResponse;
import edu.java.exception.ChatAlreadyRegisteredException;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkAlreadyAddedException;
import edu.java.exception.LinkNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handleChatAlreadyRegistered(ChatAlreadyRegisteredException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setDescription("Чат уже зарегистрирован");
        errorResponse.setCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setExceptionName(e.getClass().getSimpleName());
        errorResponse.setExceptionMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkAlreadyAdded(LinkAlreadyAddedException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setDescription("Ссылка уже добавлена");
        errorResponse.setCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setExceptionName(e.getClass().getSimpleName());
        errorResponse.setExceptionMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkNotFound(LinkNotFoundException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setDescription("Ссылка не найдена");
        errorResponse.setCode(HttpStatus.NOT_FOUND.toString());
        errorResponse.setExceptionName(e.getClass().getSimpleName());
        errorResponse.setExceptionMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatNotFound(ChatNotFoundException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setDescription("Чат не найден");
        errorResponse.setCode(HttpStatus.NOT_FOUND.toString());
        errorResponse.setExceptionName(e.getClass().getSimpleName());
        errorResponse.setExceptionMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

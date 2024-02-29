package edu.java.client;

import edu.java.dto.StackOverflowQuestionResponse;
import reactor.core.publisher.Mono;

public interface StackOverflowClient {
    Mono<StackOverflowQuestionResponse> fetchQuestion(String questionId);
}

package edu.java.client;

import edu.java.dto.api.external.StackOverflowQuestionResponse;

public interface StackOverflowClient {
    StackOverflowQuestionResponse fetchQuestion(String questionId);
}

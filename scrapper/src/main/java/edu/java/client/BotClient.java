package edu.java.client;

import edu.java.dto.api.request.LinkUpdateRequest;

public interface BotClient {

    String sendUpdate(LinkUpdateRequest linkUpdateRequest);
}

package me.mircoporetti.elmai.domain.salaries;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class AIAssistant {

    private final AIChat aiChat;

    public AIAssistant(AIChat aiChat) {
        this.aiChat = aiChat;
    }

    public Flux<String> chat(String message) {
        return aiChat.chat(message);
    }
}

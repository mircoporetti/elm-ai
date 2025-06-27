package me.mircoporetti.elmai.domain.salaries;

import reactor.core.publisher.Flux;

public class AIAssistant implements AIChat {
    @Override
    public Flux<String> chat(String message) {
        return null;
    }
}

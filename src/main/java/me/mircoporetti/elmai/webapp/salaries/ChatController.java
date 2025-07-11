package me.mircoporetti.elmai.webapp.salaries;

import me.mircoporetti.elmai.domain.salaries.AIAssistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    private final AIAssistant aiAssistant;

    public ChatController(AIAssistant aiAssistant) {
        this.aiAssistant = aiAssistant;
    }

    @PostMapping("/completion")
    public Flux<String> chat(@RequestBody ChatCompletionRequest request) {
        LOGGER.info("Received message: {}", request.message());
        return aiAssistant.chat(request.message());
    }
}

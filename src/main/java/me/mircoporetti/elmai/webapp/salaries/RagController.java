package me.mircoporetti.elmai.webapp.salaries;

import me.mircoporetti.elmai.domain.salaries.AIAssistant;
import me.mircoporetti.elmai.persistence.salaries.VectorStoreAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    private final AIAssistant aiAssistant;
    private final VectorStoreAdapter vectorStoreAdapter;
    private final ResourceLoader resourceLoader;

    public ChatController(AIAssistant aiAssistant, VectorStoreAdapter vectorStoreAdapter, ResourceLoader resourceLoader) {
        this.aiAssistant = aiAssistant;
        this.vectorStoreAdapter = vectorStoreAdapter;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping
    public Flux<String> chat(@RequestParam(defaultValue = "What is ELM?") String message) {
        return aiAssistant.chat(message);
    }

    @PostMapping("/load")
    public void load() throws IOException {
        LOGGER.info("Storing segments...");
        Resource resource = resourceLoader.getResource("classpath:documents/ELM_5.0_FA_i_Richtlinien_20200331_20240312.pdf");
        vectorStoreAdapter.storeSegments(resource);
        LOGGER.info("Segments saved");
    }
}

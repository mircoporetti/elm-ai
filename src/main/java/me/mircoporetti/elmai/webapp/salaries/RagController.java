package me.mircoporetti.elmai.webapp.salaries;

import me.mircoporetti.elmai.infrastructure.persistence.VectorStoreAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/rag")
@CrossOrigin(origins = "*")
public class RagController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RagController.class);
    public static final String ELM_DOC_PATH = "classpath:documents/ELM_5.0_FA_i_Richtlinien_20200331_20240312.pdf";

    private final VectorStoreAdapter vectorStoreAdapter;
    private final ResourceLoader resourceLoader;

    public RagController(VectorStoreAdapter vectorStoreAdapter, ResourceLoader resourceLoader) {
        this.vectorStoreAdapter = vectorStoreAdapter;
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/load")
    public void load() throws IOException {
        LOGGER.info("Storing segments...");
        Resource resource = resourceLoader.getResource(ELM_DOC_PATH);
        vectorStoreAdapter.storeSegments(resource);
        LOGGER.info("Segments saved");
    }
}

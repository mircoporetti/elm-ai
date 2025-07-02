package me.mircoporetti.elmai.infrastructure.persistence;

import dev.langchain4j.data.document.BlankDocumentException;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class VectorStoreAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(VectorStoreAdapter.class);

    private final EmbeddingStoreIngestor embeddingStoreIngestor;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public VectorStoreAdapter(EmbeddingStoreIngestor embeddingStoreIngestor,
                              EmbeddingStore<TextSegment> embeddingStore) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
        this.embeddingStore = embeddingStore;
    }

    public void storeSegments(Resource resource) throws IOException {
        LOGGER.info("Removing all segments...");
        embeddingStore.removeAll();

        try {
            LOGGER.info("Loading document from File System...");
            try (InputStream inputStream = resource.getInputStream()) {
                Path tempFile = Files.createTempFile("elm-doc", ".pdf");
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

                Document document = FileSystemDocumentLoader.loadDocument(tempFile);
                LOGGER.info("Ingesting document...");
                embeddingStoreIngestor.ingest(document);

                tempFile.toFile().deleteOnExit();
            }
            LOGGER.info("Document ingested");
        } catch (BlankDocumentException e) {
            LOGGER.error("Document is empty");
        }
    }
}
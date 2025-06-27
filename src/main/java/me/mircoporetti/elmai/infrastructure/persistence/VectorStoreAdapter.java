package me.mircoporetti.elmai.infrastructure.salaries;

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
            Document document = FileSystemDocumentLoader.loadDocument(resource.getFile().toPath());
            
            LOGGER.info("Ingesting document...");
            embeddingStoreIngestor.ingest(document);
            LOGGER.info("Document ingested");
        } catch (BlankDocumentException e) {
            LOGGER.error("Document is empty");
        }
    }
}
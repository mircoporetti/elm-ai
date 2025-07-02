package me.mircoporetti.elmai.infrastructure.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {
    private final EmbeddingModel embeddingModel;

    public PersistenceConfig(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return chatId -> MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(
            PGVectorConfig pgVectorConfig
    ) {

        return PgVectorEmbeddingStore.builder()
                .host(pgVectorConfig.getHost())
                .port(pgVectorConfig.getPort())
                .database(pgVectorConfig.getDatabase())
                .user(pgVectorConfig.getUser())
                .password(pgVectorConfig.getPassword())
                .table("elm_salaries_embeddings")
                .dimension(embeddingModel.dimension())
                .createTable(true)
                .build();
    }
}
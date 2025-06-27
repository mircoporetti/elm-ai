package me.mircoporetti.elmai.infrastructure.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {
    private final EmbeddingModel embeddingModel;

    public MemoryConfig(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return chatId -> MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(
            @Value("${pgvector.host}") String host,
            @Value("${pgvector.port}") int port,
            @Value("${pgvector.database}") String database,
            @Value("${pgvector.user}") String user,
            @Value("${pgvector.password}") String password
    ) {
        return PgVectorEmbeddingStore.builder()
                .host(host)
                .port(port)
                .database(database)
                .user(user)
                .password(password)
                .table("elm_salaries_embeddings")
                .dimension(embeddingModel.dimension())
                .createTable(true)
                .build();
    }
}
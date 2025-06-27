package me.mircoporetti.elmai.infrastructure.config;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.huggingface.HuggingFaceEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.time.Duration.ofSeconds;

@Configuration
public class OpenAIConfig {
    private static final String MODEL_NAME = "gpt-4o-mini";
    public static final String API_KEY = "OPENAI_API_KEY";

    @Bean
    public StreamingChatModel streamingChatLanguageModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(System.getenv(API_KEY))
                .modelName(MODEL_NAME)
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        return HuggingFaceEmbeddingModel.builder()
                .accessToken(System.getenv("HF_API_KEY"))
                .modelId("sentence-transformers/all-MiniLM-L6-v2")
                .waitForModel(true)
                .timeout(ofSeconds(60))
                .build();
    }
}
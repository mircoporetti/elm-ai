package me.mircoporetti.elmai.infrastructure.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.huggingface.HuggingFaceEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.time.Duration.ofSeconds;

@Configuration
public class AIModelConfig {
    @Value("${elm-ai.hugging-face-api-key}")
    public String hfApiKey;
    @Value("${elm-ai.openai-api-key}")
    public String openApiKey;

    @Bean
    public StreamingChatModel streamingChatLanguageModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(openApiKey)
                .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        return HuggingFaceEmbeddingModel.builder()
                .accessToken(hfApiKey)
                .modelId("sentence-transformers/all-MiniLM-L6-v2")
                .waitForModel(true)
                .timeout(ofSeconds(60))
                .build();
    }
}
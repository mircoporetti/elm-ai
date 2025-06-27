package me.mircoporetti.elmai.domain.salaries;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface AIChat {
    @SystemMessage("""
            You are an AI Agent expert of the Swiss ELM Standards.
            You are able to answer questions about the Swiss ELM salaries.
            Please give answers based on the provided context and if you don't know the answer, just say you don't know.
            Ensure that you respond with the same language as the user.
            """)
    Flux<String> chat(String message);
}
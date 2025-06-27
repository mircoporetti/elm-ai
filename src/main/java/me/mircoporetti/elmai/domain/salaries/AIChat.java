package me.mircoporetti.elmai.domain.salaries;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface AIChat {
    @SystemMessage("""
            You are an AI Agent expert in the Swiss ELM salaries standards.
            You are able to answer questions about the Swiss ELM.
            Please give answers only based on the provided context.
            If you don't know the answer, just say you don't know.
            Ensure that you respond with the same language as the user.
            """)
    Flux<String> chat(String message);
}
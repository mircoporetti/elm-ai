package me.mircoporetti.elmai.webapp.salaries;

import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import io.restassured.RestAssured;
import me.mircoporetti.elmai.domain.salaries.AIAssistant;
import me.mircoporetti.elmai.webapp.TestContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerIntegrationTest extends TestContainerTest {

    @LocalServerPort
    private int port;

    @MockitoBean
    private AIAssistant aiAssistant;

    @Autowired
    private EmbeddingStoreContentRetriever contentRetriever;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturnMessageFromAIChat() {
        String userMessage = "Hello Dude!";
        String aiResponse = "Hello Human!";

        when(aiAssistant.chat(userMessage)).thenReturn(Flux.just(aiResponse));

        given()
            .param("message", userMessage)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .get("/chat")
        .then()
            .statusCode(200)
            .body(equalTo(aiResponse));
    }

    @Test
    public void shouldLoadDocumentAndStoreSegments() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .post("/chat/load")
        .then()
            .statusCode(200);

        var contents = contentRetriever.retrieve(Query.from("ELM"));
        assertFalse(contents.isEmpty());
    }
}

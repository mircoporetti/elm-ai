package me.mircoporetti.elmai.webapp.salaries;

import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import io.restassured.RestAssured;
import me.mircoporetti.elmai.webapp.TestContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RagControllerIntegrationTest extends TestContainerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmbeddingStoreContentRetriever contentRetriever;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void shouldLoadDocumentAndStoreSegments() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .post("/rag/load")
        .then()
            .statusCode(200);

        var contents = contentRetriever.retrieve(Query.from("ELM"));
        assertFalse(contents.isEmpty());
    }
}

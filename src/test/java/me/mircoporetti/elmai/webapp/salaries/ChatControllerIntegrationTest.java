package me.mircoporetti.elmai.webapp.salaries;

import io.restassured.RestAssured;
import me.mircoporetti.elmai.domain.salaries.AIChat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AIChat aiChat;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturnMessageFromAIChat() {
        // Given
        String userMessage = "Hello AI";
        String aiResponse = "Hello Human";

        when(aiChat.chat(userMessage)).thenReturn(Flux.just(aiResponse));

        // When & Then
        given()
            .param("message", userMessage)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .get("/chat")
        .then()
            .statusCode(200)
            .body(equalTo(aiResponse));
    }
}

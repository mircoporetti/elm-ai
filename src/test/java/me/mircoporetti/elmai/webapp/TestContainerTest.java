package me.mircoporetti.elmai.webapp;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class TestContainerTest {
    static final DockerImageName PGVECTOR_IMAGE = DockerImageName
            .parse("ankane/pgvector:v0.5.1")
            .asCompatibleSubstituteFor("postgres");
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(PGVECTOR_IMAGE)
            .withDatabaseName("postgres")
            .withUsername("user")
            .withPassword("password")
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(
                            new PortBinding(Ports.Binding.bindPort(5433), new ExposedPort(5432))
                    )
            ));
    @Value("${pgvector.port}")
    String dbPort;

    @DynamicPropertySource
    static void pgVectorProps(DynamicPropertyRegistry registry) {
        registry.add("pgvector.host", postgres::getHost);
        registry.add("pgvector.port", () -> postgres.getMappedPort(5432));
        registry.add("pgvector.database", postgres::getDatabaseName);
        registry.add("pgvector.user", postgres::getUsername);
        registry.add("pgvector.password", postgres::getPassword);
    }
}

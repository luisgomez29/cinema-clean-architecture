package co.com.luisgomez29.api;

import co.com.luisgomez29.api.config.ApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import tools.jackson.databind.json.JsonMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;

@ActiveProfiles("test")
@EnableConfigurationProperties(ApiProperties.class)
public class BaseIntegration {
    @Autowired
    protected ApiProperties properties;

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected JsonMapper mapper;

    @SuppressWarnings("unchecked")
    protected <T> T loadFileConfig(String filename, Class<T> clazz) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            var inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
            String str = new BufferedReader(inputStreamReader).lines().collect(Collectors.joining("\n"));
            if (clazz.equals(String.class)) {
                return (T) str;
            }

            return mapper.readValue(str, clazz);
        } catch (IOException e) {
            fail(e.getMessage());
            return null;
        }
    }

    protected StatusAssertions statusAssertionsWebClientGet(String path, Object... uriVariables) {
        return webTestClient.get()
                .uri(path, uriVariables)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus();
    }

    protected StatusAssertions statusAssertionsWebClientPost(String path, String requestBody) {
        return webTestClient.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus();
    }

    protected StatusAssertions statusAssertionsWebClientPut(String path, String requestBody, Object... uriVariables) {
        return webTestClient.put()
                .uri(path, uriVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus();
    }

    protected StatusAssertions statusAssertionsWebClientDelete(String path, String requestBody) {
        return webTestClient
                .method(HttpMethod.DELETE)
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus();
    }
}

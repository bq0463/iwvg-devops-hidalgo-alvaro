package es.upm.miw.devops.functionaltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserResourceFT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetUserById() {
        webTestClient.get()
                .uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.fractions").isArray();
    }

    @Test
    void testDeleteUser() {
        webTestClient.get()
                .uri("/user/4")
                .exchange()
                .expectStatus().isOk();

        webTestClient.delete()
                .uri("/user/4")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri("/user/4")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testDeleteUserNotFound() {
        webTestClient.delete()
                .uri("/user/999")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testUpdateActive() {
        webTestClient.get()
                .uri("/user/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(true);

        webTestClient.put()
                .uri("/user/2/active")
                .bodyValue("{\"active\": false}")
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(false);

        webTestClient.get()
                .uri("/user/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(false);
    }

    @Test
    void testUpdateActiveUserNotFound() {
        webTestClient.put()
                .uri("/user/999/active")
                .bodyValue("{\"active\": true}")
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().is5xxServerError();
    }
}

package graalpy.demo;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
final class ReviewControllerTest {
    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void analyzeEndpointAcceptsReviewText() {
        HttpRequest<?> request = HttpRequest.POST(
                "/api/reviews/analyze",
                new ReviewRequest("This monitor looks great, and the screen is bright and sharp.")
            )
            .contentType(MediaType.APPLICATION_JSON_TYPE);

        ReviewAnalysisView response = httpClient.toBlocking().retrieve(request, ReviewAnalysisView.class);
        assertEquals("positive", response.label());
    }

    @Test
    void analyzeEndpointRejectsBlankReviewText() {
        HttpRequest<?> request = HttpRequest.POST(
                "/api/reviews/analyze",
                new ReviewRequest("")
            )
            .contentType(MediaType.APPLICATION_JSON_TYPE);

        HttpClientResponseException exception = assertThrows(
            HttpClientResponseException.class,
            () -> httpClient.toBlocking().exchange(request)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}

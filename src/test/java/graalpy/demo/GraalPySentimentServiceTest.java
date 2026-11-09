package graalpy.demo;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
final class GraalPySentimentServiceTest {
    @Inject
    GraalPySentimentService graalPySentimentService;

    @Test
    void analyzesPositiveReview() {
        ReviewAnalysisView result = graalPySentimentService.analyze(
            """
                I bought this coffee grinder last week and I am really happy with it.
                It feels solid, the grind is consistent, and setup took less than five minutes.
                The lid is a little noisy, but overall it feels like a great value.
                """
        );

        assertEquals("positive", result.label());
        assertTrue(result.compound() > 0.05);
    }
}

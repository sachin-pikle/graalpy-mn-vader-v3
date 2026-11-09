package graalpy.demo;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.util.Map;

@Controller("/api/reviews")
final class ReviewController {
    private final GraalPySentimentService graalPySentimentService;

    ReviewController(GraalPySentimentService graalPySentimentService) {
        this.graalPySentimentService = graalPySentimentService;
    }

    @Post(uri = "/analyze", consumes = MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.BLOCKING)
    HttpResponse<?> analyze(@Body ReviewRequest request) {
        if (request == null || request.text() == null || request.text().isBlank()) {
            return HttpResponse.badRequest(Map.of("message", "Review text must not be blank."));
        }

        return HttpResponse.ok(graalPySentimentService.analyze(request.text()));
    }
}

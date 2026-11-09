package graalpy.demo;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ReviewAnalysisView(
    String label,
    double compound,
    double positive,
    double neutral,
    double negative
) {
}

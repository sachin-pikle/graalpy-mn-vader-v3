package graalpy.demo;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ReviewRequest(String text) {
}

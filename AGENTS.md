# Purpose

Keep this repository focused on one example: a Micronaut Java application calling Python VADER through GraalPy.

# Current Sample

- Artifact and application name: `graalpy-mn-vader-v3`
- Micronaut: 5.2.4
- Java: 25
- GraalPy Maven plugin and runtime: 25.4.4.1.1
- Python package: `vaderSentiment==3.3.2`
- Endpoint: `POST /api/reviews/analyze`
- Request: JSON containing review text
- Response: flat VADER scores and label

# Pattern To Preserve

1. Serve the static GUI from `src/main/resources/public`.
2. Accept `{"text":"..."}` at `/api/reviews/analyze`.
3. Validate blank text in the Java controller.
4. Call a typed injected `@GraalPyModule` interface from the Java service.
5. Keep the Python module under `src/main/resources/org.graalvm.python.vfs/src/`.
6. Return JSON from Python and deserialize it into a small `@Serdeable` Java record.
7. Keep `proxy-config.json` aligned with the GraalPy module interface.
8. Keep the GUI and sample reviews suitable for a short conference demo.

# Boundaries

- Do not replace `@GraalPyModule` with manual `Context`, `Value`, `eval`, or bindings code.
- Do not add database, vector-search, benchmark, or orchestration features here.
- Keep Python packages pinned.
- Keep Micronaut, GraalVM, GraalPy, and Java versions aligned when upgrading.
- Keep JVM jar and native-image packaging available.

# Verify

```bash
sdk use java 25.4.4+1-graal
./mvnw test
./mvnw mn:run
```

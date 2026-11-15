# Demo: VADER Sentiment Analysis with GraalPy and Micronaut

<small>version: v3</small>

This sample is a Micronaut Java service that calls a Python VADER function through an injected `@GraalPyModule` interface. The browser UI sends review text as JSON and displays the sentiment result.

## Versions

- Micronaut Platform 5.2.6
- Java 25
- GraalVM SDKMAN version `25.5.5.0+3-graal`
- GraalPy Maven plugin and runtime 25.5.5.0.3
- Python package `vaderSentiment==3.3.2`

## Flow

```text
Browser
  → Java controller
  → Java service
  → @GraalPyModule interface
  → Python VADER function
```

The browser reads the selected review file and sends its text as JSON. Here’s an example:

```json
{"text":"This coffee grinder is fantastic."}
```

The endpoint returns the VADER result directly:

```json
{
  "label": "positive",
  "compound": 0.5574,
  "positive": 0.474,
  "neutral": 0.526,
  "negative": 0.0
}
```

## Key Files

- `pom.xml` — Micronaut, GraalPy and VADER versions and build configuration
- `src/main/java/graalpy/demo/ReviewController.java` — HTTP endpoint and blank-text check
- `src/main/java/graalpy/demo/GraalPySentimentService.java` — calls Python and maps its JSON result
- `src/main/java/graalpy/demo/SentimentModule.java` — injected `@GraalPyModule` interface
- `src/main/java/graalpy/demo/ReviewAnalysisView.java` — response type
- `src/main/resources/org.graalvm.python.vfs/src/sentiment_app.py` — Python VADER function
- `src/main/resources/public/` — browser UI
- `samples/` — positive and negative reviews for the demo

## VS Code

This project disables Java extension autobuild in `.vscode/settings.json` while the generated Serde compilation issue is investigated. See [Micronaut Serialization issue #1492](https://github.com/micronaut-projects/micronaut-serialization/issues/1492).

## Run Locally

Select the project GraalVM with SDKMAN:

```bash
sdk use java 25.5.5.0+3-graal
```

Run the tests:

```bash
./mvnw clean test
```

Start the application:

```bash
./mvnw mn:run
```

Open [http://localhost:8080](http://localhost:8080), then choose a review file from `samples/` in the browser.

The first build needs network access to download Maven and GraalPy dependencies and install the pinned VADER package.

## API

`POST /api/reviews/analyze` accepts review text as JSON and returns the VADER scores and label.

Test the endpoint while the application is running:

```bash
curl -X POST http://localhost:8080/api/reviews/analyze \
  -H 'Content-Type: application/json' \
  -d '{"text":"I love this coffee grinder. It works perfectly!"}'
```

The response is JSON with `label`, `compound`, `positive`, `neutral`, and `negative` fields.

Send blank text and include the response status:

```bash
curl -i -X POST http://localhost:8080/api/reviews/analyze \
  -H 'Content-Type: application/json' \
  -d '{"text":""}'
```

The response status is `400 Bad Request`.

## Executable JAR

Build the executable JAR:

```bash
./mvnw clean package
```

Run it:

```bash
java -jar target/graalpy-mn-vader-v3-0.1.jar
```

The JAR contains the Python module and the installed VADER package in GraalPy's virtual filesystem.

## Inspect the JAR in JD-GUI

```bash
java -jar jd-gui-1.6.6.jar
```

When JD-GUI opens, open `target/graalpy-mn-vader-v3-0.1.jar`.

Look for these paths inside the JAR:

- `org.graalvm.python.vfs/src/sentiment_app.py`
- `org.graalvm.python.vfs/venv/`

These show the Python module and installed VADER package in GraalPy’s virtual filesystem inside the executable JAR.

## Native Image

Build the native executable:

```bash
./mvnw clean package -Dpackaging=native-image
```

Run it:

```bash
./target/graalpy-mn-vader-v3
```

## Differences from v2

- The browser sends review text as JSON; v2 accepted a text-file upload as multipart data.
- The endpoint returns the sentiment result directly, without a filename wrapper.
- The browser workflow and injected `@GraalPyModule` integration are retained.

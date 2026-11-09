# GraalPy VADER v3

A Micronaut application that calls VADER through an injected GraalPy module.

Uses GraalPy 25.4.4.1.1 and VADER 3.3.2.

## Flow

```text
Browser
  → Java controller
  → Java service
  → @GraalPyModule interface
  → Python VADER function
```

The browser reads the selected review file and sends its text as JSON:

```json
{"text":"This coffee grinder is fantastic."}
```

The endpoint returns the VADER result directly:

```json
{"label":"positive","compound":0.5984,"positive":0.565,"neutral":0.435,"negative":0.0}
```

## Run

```bash
sdk use java 25.4.4+1-graal
./mvnw test
./mvnw mn:run
```

Open [http://localhost:8080](http://localhost:8080).

Sample reviews are under `samples/`.

## API

```bash
curl -X POST http://localhost:8080/api/reviews/analyze \
  -H 'Content-Type: application/json' \
  -d '{"text":"I love this coffee grinder. It works perfectly!"}'
```

## Package

Build and run the executable jar:

```bash
./mvnw package
java -jar target/graalpy-mn-vader-v3-0.1.jar
```

The jar contains the Python module and installed VADER package under `org.graalvm.python.vfs/`.

Build a native executable:

```bash
./mvnw package -Dpackaging=native-image
./target/graalpy-mn-vader-v3
```

## Changes from v2

- Accepts review text as JSON instead of a multipart file upload.
- Returns a flat sentiment result without the filename wrapper.
- Keeps the same browser workflow and `@GraalPyModule` integration.

# Notification Service Library – Technical Challenge

## Project Description
This project implements a **notification service library** in Java that supports sending messages via multiple channels (Email, SMS, Push) with pluggable providers. The library allows synchronous and asynchronous sending, validation of messages, and mock implementations for testing and demonstration purposes.

The main goal is to provide a flexible and extensible solution for sending notifications across different providers without coupling client code to specific implementations.

---

## Problem Statement
The challenge consists of designing a notification system that:

- Supports multiple channels (Email, SMS, Push).
- Handles message validation (email format, phone number, non-empty body, etc.).
- Supports pluggable providers via a `SenderFactory`.
- Supports asynchronous sending (`CompletableFuture`).
- Can be extended easily to add new providers.
- Optional: Dockerfile for easy execution, retry mechanism, templates, and notification state reporting.

---

## Solution Architecture
The library is implemented using **Java 17** and follows **Clean Architecture principles**:

- **Domain layer**: contains `NotificationMessage`, `Recipient`, `Channel`, and exceptions.
- **Application layer**: provides `NotificationService` implementing the `SendNotificationUseCase`.
- **Infrastructure layer**: contains concrete sender implementations (`SendGridEmailSenderMock`, `TwilioSmsSenderMock`, `FcmPushSenderMock`) and the `SenderFactory`.
- **Utilities**: `ValidationUtils` handles validation rules.

**Key Design Decisions:**

1. **SenderFactory**: allows registering multiple providers using a key (`email:sendgrid`, `sms:twilio`, `push:fcm`).
2. **ValidationUtils**: ensures messages and recipients are valid before sending; validation errors do **not** trigger retries.
3. **Retry mechanism**: implemented internally in `NotificationService` for operational failures only.
4. **Asynchronous sending**: `sendAsync` uses `CompletableFuture` for non-blocking execution.
5. **Mocks**: all provider implementations are mocks, allowing safe testing without real API calls.

---

## Dependencies and Requirements
- Java 17 or higher
- Maven (for build and dependency management)
- JUnit 5 (unit testing)
- Mockito (for mocking dependencies in unit testing)
- Optional: Docker (for running examples)

---

## Installation

Clone the repository:

```bash
  git clone https://github.com/turcoamado/notification-library-challenge.git
  cd notification-service
```

## Build the project and run tests
```bash
  mvn clean test
```

## Quick Start
Example usage of the library:
```java
SenderFactory factory = new SenderFactory();
factory.register("email:sendgrid", new SendGridEmailSenderMock("fake-api-key"));
factory.register("sms:twilio", new TwilioSmsSenderMock("sid","token"));
factory.register("push:fcm", new FcmPushSenderMock("fcm-key"));

SendNotificationUseCase service = new NotificationService(factory);

NotificationMessage email = new NotificationMessage.Builder()
        .channel(Channel.EMAIL)
        .subject("Test Notification")
        .body("Hello World!")
        .build();

NotificationResult result = service.send("email:sendgrid", email, new Recipient("user@example.com"));
System.out.println(result);

```
For asynchronous sending:
```java
CompletableFuture<NotificationResult> asyncResult = service.sendAsync("email:sendgrid", email, new Recipient("user@example.com"));

asyncResult.thenAccept(res -> System.out.println("Async result: " + res));
asyncResult.join(); // Wait for completion

```

## Configuration

- **Email (SendGrid)**: provide `apiKey`
- **SMS (Twilio)**: provide `sid` and `token`
- **Push (FCM)**: provide `serverKey`

All keys are injected when registering the provider with `SenderFactory`:

```java
factory.register("email:sendgrid", new SendGridEmailSenderMock("YOUR_API_KEY"));
```
## Supported Providers
| Channel | Provider      | Required Credentials |
| ------- | ------------- | -------------------- |
| Email   | SendGridMock  | apiKey               |
| SMS     | TwilioSmsMock | sid, token           |
| Push    | FcmPushMock   | serverKey            |
You can implement your own providers by implementing NotificationSender and registering them via SenderFactory.

## API Reference
### Core Interfaces and Classes

- `SendNotificationUseCase` – main interface with `send()` and `sendAsync()`.
- `NotificationService` – default implementation with internal retry mechanism.
- `SenderFactory` – registry of providers, resolves sender by key.
- `NotificationSender` – interface for provider implementations.
- `NotificationMessage` – message object with channel, subject, and body.
- `Recipient` – recipient object with validation.

### Retry Mechanism
`NotificationService` internally retries operational failures (network/API errors) up to 3 times by default. Validation errors (e.g., invalid email format) are not retried.

### Asynchronous Sending
`sendAsync()` returns a `CompletableFuture<NotificationResult>` allowing non-blocking execution and callback handling.

## Docker
A Dockerfile is provided to run the examples quickly:

```bash
  docker build -t notifications-lib .
  docker run --rm notifications-lib
```
The container runs the example class that demonstrates sending messages to all channels.

## Example Output
Example output from running the provided NotificationExamples class:
```
[SendGridMock] Sending email to user@example.com subject=Test Notification
Result: NotificationResult[success=true, provider=SendGridMock, messageId=sg-1765755214522, errorMessage=null]
[TwilioMock] Sending SMS to +5491112345678 body=New SMS notification
Result: NotificationResult[success=true, provider=TwilioMock, messageId=tw-1765755214539, errorMessage=null]
[FcmMock] Sending push to device-token-1234567 body=New Push notification
Result: NotificationResult[success=true, provider=FcmMock, messageId=fcm-1765755214539, errorMessage=null]
[SendGridMock] Sending email to user@example.com subject=Test Notification
Async result: NotificationResult[success=true, provider=SendGridMock, messageId=sg-1765755215042, errorMessage=null]
```

## Example Output
- **Credentials:** Inject programmatically; do not store in source code.
- **Validation:** All messages and recipients are validated before sending.
- **Retry:** Only operational failures are retried, preventing repeated invalid data sending.

## Notes
- The library is extensible: new providers can be added by implementing `NotificationSender`.
- All current providers are mocks for save testing.
- `sendAsync()` demonstrates asynchronous usage.
- Dockerfile allows running examples without local setup.
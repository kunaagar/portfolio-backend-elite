# elit-error-manager

## Overview

`elit-error-manager` provides a **standardized, enterprise-grade error handling framework** for Spring Boot microservices.

It ensures:

* Consistent API error responses
* Strong error contracts for consumers
* First-class observability (traceId, spanId, correlationId)
* Clean separation of concerns between business logic and error handling

---

## ApiErrorResponseDTO – Field Specification

### 1. `timestamp`

**Type:** ISO-8601 string
**Example:** `2026-01-06T18:34:12.219+05:30`

**Why it exists:**

* Enables timeline correlation across logs, metrics, and traces
* Required for audits and incident analysis

---

### 2. `status`

**Type:** Integer (HTTP Status Code)
**Example:** `400`

**Why it exists:**

* Represents transport-level failure
* Allows gateways, SDKs, and clients to act correctly

⚠️ **Do not encode business meaning here**

---

### 3. `errorCode`

**Type:** String
**Example:** `CUS_400_001`

**Why it exists (MOST IMPORTANT FIELD):**

* Stable contract for frontend, integrations, and automation
* Enables localization without breaking clients
* Enables alerting & dashboards

**Recommended format:**

```
<SERVICE>_<HTTP_STATUS>_<SEQUENCE>
```

---

### 4. `message`

**Type:** String
**Example:** `Invalid customerId provided`

**Why it exists:**

* Human-readable explanation
* Safe to display to end users
* No stack traces, no internals

---

### 5. `path`

**Type:** String
**Example:** `/v1/customers/123`

**Why it exists:**

* Identifies failing API
* Extremely useful in support tickets

---

### 6. `correlationId`

**Type:** String
**Source:** Request Header (e.g. `X-Correlation-ID`)

**Why it exists:**

* Cross-system correlation (API Gateway → Services → DB)
* Business-friendly identifier

---

### 10. `fieldErrors`

**Type:** Array (Optional)

```json
[
  {
    "field": "email",
    "message": "must be a valid email address",
    "rejectedValue": "inva.lid@mail.com"
  }
]
```

**Why it exists:**

* Validation and client-side actionable feedback
* Prevents vague “Bad Request” responses

---

## What This Starter Intentionally Does NOT Do

❌ Does not expose stack traces
❌ Does not leak internal exception names
❌ Does not depend on service-specific logic
❌ Does not force logging strategy

---

## Observability Integration

This starter is designed to work seamlessly with:

* `micrometer-tracing`
* MDC-based logging
* OpenTelemetry / Brave

All error responses automatically include:

* `traceId`
* `spanId`
* `correlationId`

---

## Customization

Services may:

* Add new error codes
* Extend `EliteException`
* Override default handlers if required

All beans are conditional and override-safe.

---

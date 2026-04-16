## Smart Campus Sensor & Room Management API

A RESTful API built with **JAX-RS (Jersey)** for managing campus Rooms and Sensors as part of the Informatics Institute of Technology (IIT) Smart Campus initiative.

### API Overview
This API facilitates the digital management of physical campus infrastructure:
* **Rooms**: Physical spaces (e.g., `LIB-301`, `LAB-01`) where hardware is deployed.
* **Sensors**: IoT devices (Temperature, CO2, Occupancy) associated with specific rooms.
* **SensorReadings**: Historical time-series data captured by each sensor.

**Base URL:** `http://localhost:8080/CSA-Coursework-SmartCampus/api/v1`

---

### Project Structure
```text
CSA-Coursework-SmartCampus/
├── src/main/java/com/mycompany/csa/coursework/smartcampus/
│   ├── models/           (Room, Sensor, SensorReading POJOs)
│   ├── repository/       (DataRepository using ConcurrentHashMap)
│   ├── resources/        (Discovery, Room, Sensor, and Reading Resources)
│   ├── exceptions/       (Custom Exceptions and dedicated ExceptionMappers)
│   ├── filter/           (LoggingFilter for API observability)
│   └── SmartCampusApplication.java (@ApplicationPath config)
├── src/main/webapp/WEB-INF/
│   └── web.xml           (Deployment Descriptor)
└── pom.xml               (Maven Project Configuration)
```

---

### How to Build and Run
**Prerequisites:** JDK 8+, Maven 3.6+, Apache Tomcat 9.x.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/NimsaraRavindu/CSA-SmartCampus.git
    ```
2.  **Build:** Run `mvn clean install` or right-click in NetBeans and select **Clean and Build**.
3.  **Deploy:** Deploy the generated `.war` file to your Tomcat server.
4.  **Access:** Navigate to `http://localhost:8080/CSA-Coursework-SmartCampus/api/v1`

---

### API Endpoints

| Category | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| **Discovery** | GET | `/api/v1` | HATEOAS metadata and resource links |
| **Rooms** | GET | `/rooms` | List all rooms |
| | POST | `/rooms` | Create room (**Returns 201 + Location Header**) |
| | DELETE | `/rooms/{id}` | Delete room (**Blocked if sensors exist - 409**) |
| **Sensors** | GET | `/sensors` | List all sensors (Supports `?type=` filter) |
| | POST | `/sensors` | Register sensor (**Validates roomId - 422**) |
| **Readings**| POST | `/sensors/{id}/readings` | Add reading (**Blocked if Maintenance - 403**) |
| | GET | `/sensors/{id}/readings` | Fetch historical logs for a specific sensor |

---

### Sample curl Commands

**1. API Discovery**
```bash
curl -X GET http://localhost:8080/CSA-Coursework-SmartCampus/api/v1 -H "Accept: application/json"
```

**2. Create a New Room**
```bash
curl -i -X POST http://localhost:8080/CSA-Coursework-SmartCampus/api/v1/rooms \
  -H "Content-Type: application/json" \
  -d '{"id":"ROOM-TEMP","name":"Temporary Lab","capacity":25}'
```

**3. Register a Sensor (Dependency Validation)**
```bash
curl -X POST http://localhost:8080/CSA-Coursework-SmartCampus/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d '{"id":"TEMP-001","type":"Temperature","status":"ACTIVE","currentValue":22.5,"roomId":"ROOM-TEMP"}'
```

**4. Filter Sensors (QueryParam Test)**
```bash
curl -X GET "http://localhost:8080/CSA-Coursework-SmartCampus/api/v1/sensors?type=Temperature" -H "Accept: application/json"
```

**5. Add a Reading (Sub-Resource Test)**
```bash
curl -X POST http://localhost:8080/CSA-Coursework-SmartCampus/api/v1/sensors/TEMP-001/readings \
  -H "Content-Type: application/json" \
  -d '{"value":26.8}'
```

**6. Delete Room with Sensors (Expect 409 Conflict)**
```bash
curl -i -X DELETE http://localhost:8080/CSA-Coursework-SmartCampus/api/v1/rooms/ROOM-TEMP -H "Accept: application/json"
```

---

### Report: Answers to Coursework Questions

**Part 1.1 — JAX-RS Resource Lifecycle**
By default, JAX-RS Resource classes (like SensorRoomResource) follow a Request-Scoped lifecycle.
This implies that the JAX-RS runtime will generate a new instance of the class per incoming HTTP request
and will discard it after the response has been delivered. Since the instances are not stateful, the in-
memory data must be stored as maps and lists in a centralized and static context (like a DataRepository
with immutable members). This architectural choice requires the application of thread-safe data
structures, like ConcurrentHashMap to ensure that there are not race conditions when multiple
simultaneous requests may want to update the data simultaneously, resulting in the loss or corruption
of data.

**Part 1.2 — HATEOAS**
HATEOAS (Hypermedia as the Engine of Application State) enables an API to be self-discovery
oriented. The server uses links in the JSON responses to dynamically direct the client on what to do. This
is advantageous to client-side developers as it decouples the client and the URI structure of the server.
So, if the internal path changes, the client (which follows links) will not break and continue working. It
reduces the need for hard-coding URLs and provides a living map of the API that is always more accurate
than static, external PDF documentation.

**Part 2.1 — IDs vs Full Objects in List Responses**
Returning only IDs minimizes the response payload size, which conserves network bandwidth critical
for mobile or low-latency environments. However, it increases the API usage as the client is expected to
make a separate request to the server by a different GET to display meaningful data each time with a
different ID. Returning Full Objects adds bandwidth at the start, but much less client-side processing and
the overall round-trip count to the server, which will provide a more responsive user interface.

**Part 2.2 — Is DELETE Idempotent?**
Yes, the implementation is idempotent. An operation is idempotent if making multiple identical
requests has the same effect on the server state as a single request. In this API, the first DELETE request
for a specific room removes the resource (returning a 204 No Content). If a client mistakenly sends the
same request again, the room shall already be deleted making the second server response a404 Not
Found error. Therefore, the server state will remain unchanged (the room stays deleted).

**Part 3.1 — @Consumes and Content-Type Mismatch**
When @Consumes(MediaType.APPLICATION_JSON) is specified, JAX-RS will act as a gatekeeper. If a
client sends text/plain or application/xml, an error will be returned as HTTP 415 Unsupported Media
Type as the runtime will identify the mismatch before the resource method is even executed. This will
stop the application from crashing due to unmarshalling errors and ensure that the code only processes
data it is designed to handle.

**Part 3.2 — @QueryParam vs Path-based Filtering**
Query parameters (?type=CO2) are better filters since they introduce optional modifiers to a
collection. Using Path parameters (/type/CO2) means that there will be a strict resource hierarchy
introduced to the application. Using query parameters allows for the flexible combination of multiple
filters (?type=CO2&amp;status=ACTIVE) without creating a confusing and deep nesting scheme of the URL. It
will maintain a clean, resource-oriented URI for the collection.

**Part 4.1 — Sub-Resource Locator Pattern**
The Sub-Resource Locator pattern promotes the Single Responsibility Principle. By delegating the
logic for readings to a separate SensorReadingResource class, we prevent the SensorResource from
becoming a massive, unmaintainable file. This modularity makes the API easier to scale, test, and debug,
as each class focuses strictly on its own domain (metadata vs. historical logs).

**Part 5.2 — Why 422 is More Accurate than 404**
A 404 Not Found suggests the URI or the Endpoint itself is missing and a 422 Unprocessable Entity
indicates that the server understands the request and the JSON syntax is correct, but the business logic
is flawed (i.e., the referenced Room ID does not exist). Therefore using 422 will provide the developer
with the knowledge that the error lies in the data provided, not the target URL.

**Part 5.4 — Security Risks of Exposing Stack Traces**
Exposing stack traces will make Technical Information known to outsiders. An attacker can gather
sensitive details such as the specific version of the Tomcat server (to find known CVE exploits), internal
file system paths, and the names of internal classes and variables. This internal map will allow an
attacker to identify logic flaws for targeted injections or DOS attacks.

**Part 5.5 — Why Use Filters Instead of Manual Logging**
Manually inserting Logger.info () in every method violates the DRY principle and leads to code
repetitions. A Filter ensures that 100% of requests and responses are logged consistently without
polluting the business logic. It also makes it easier to update the logging format or security auditing rules
in one single location. Furthermore, filters provide access to the request and response contexts at the
container level, helping to capture crucial metadata and status codes even when the JAX-RS runtime
rejects a request before it reaches the resource logic.

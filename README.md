# 🚦 Routing-Graph Microservice (8081)

## Project Overview

**Routing-Graph** is a **helper microservice** for CoreRouteX. It maintains a **weighted graph of edges** and calculates **shortest paths** between locations using **Dijkstra’s algorithm**.

> Note: Users **do not interact directly** with Routing-Graph. All requests come via **CoreRouteX (8080)**.

**Key responsibilities:**
- Store edges (from → to) with distances in the database.
- Compute the **shortest distance** between two locations.
- Provide endpoints for adding edges and fetching shortest paths.

---

## Technologies Used

| Layer       | Technology                     |
|------------|--------------------------------|
| Framework  | Spring Boot 3                  |
| Database   | H2 (in-memory)                 |
| Build      | Maven                           |
| Communication | REST API                     |
| Logging    | SLF4J                           |

---

## Architecture

```text
+-------------------+
|  Routing-Graph    |
|   (Port 8081)     |
+-------------------+
| - Graph Edges     |
| - Shortest Path   |
| - Dijkstra Algo   |
+-------------------+
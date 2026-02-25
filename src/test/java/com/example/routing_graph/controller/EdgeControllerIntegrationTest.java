package com.example.routing_graph.controller;

import com.example.routing_graph.model.Edge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EdgeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddEdgeAndGetShortestDistance() {
        // Add edge A -> B
        Edge edge1 = new Edge("A", "B", 100.0);
        ResponseEntity<Edge> response1 = restTemplate.postForEntity("/api/edges", edge1, Edge.class);
        assertEquals(HttpStatus.OK, response1.getStatusCode());

        // Add edge B -> C
        Edge edge2 = new Edge("B", "C", 50.0);
        ResponseEntity<Edge> response2 = restTemplate.postForEntity("/api/edges", edge2, Edge.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());

        // Get shortest distance A -> C
        ResponseEntity<String> shortestDistanceResponse =
                restTemplate.getForEntity("/api/edges/shortest-distance?from=A&to=C", String.class);

        assertTrue(shortestDistanceResponse.getBody().contains("150.0"));
    }
}
package com.example.routing_graph.controller;

import com.example.routing_graph.model.Edge;
import com.example.routing_graph.service.EdgeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/edges")
public class EdgeController {

    private final EdgeService edgeService;
    private static final Logger logger = LoggerFactory.getLogger(EdgeController.class);

    public EdgeController(EdgeService edgeService) {
        this.edgeService = edgeService;
    }

    // Add a new edge
    @PostMapping
    public ResponseEntity<Edge> addEdge(@Valid @RequestBody Edge edge) {
        logger.info("Received edge: {} -> {} ({} km)", edge.getFromLocation(), edge.getToLocation(), edge.getDistanceInKm());
        Edge savedEdge = edgeService.saveEdge(edge);
        return ResponseEntity.ok(savedEdge);
    }

    // Get all edges
    @GetMapping
    public ResponseEntity<List<Edge>> getAllEdges() {
        logger.info("Fetching all edges");
        return ResponseEntity.ok(edgeService.getAllEdges());
    }

    // Get shortest distance between two location
    @GetMapping("/shortest-distance")
    public ResponseEntity<Map<String, Object>> getShortestDistance(
            @RequestParam String from,
            @RequestParam String to
    ) {
        logger.info("Calculating shortest distance from {} to {}", from, to);
        double distance = edgeService.calculateShortestDistance(from, to);
        Map<String, Object> response = new HashMap<>();
        response.put("from", from);
        response.put("to", to);
        response.put("shortestDistanceInKm", distance);
        return ResponseEntity.ok(response);
    }
}
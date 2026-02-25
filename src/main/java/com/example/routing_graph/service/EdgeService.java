package com.example.routing_graph.service;

import com.example.routing_graph.model.Edge;
import com.example.routing_graph.repository.EdgeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EdgeService {

    private final EdgeRepository edgeRepository;

    public EdgeService(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }
    // ---------- 1️⃣ Save edge to DB ----------
    public Edge saveEdge(Edge edge) {
        return edgeRepository.save(edge);
    }

    // ---------- 2️⃣ Get all edges ----------
    public List<Edge> getAllEdges() {
        return edgeRepository.findAll();
    }

    // ---------- 3️⃣ Get edge by ID (for unit tests) ----------
    public Edge getEdgeById(Long id) {
        return edgeRepository.findById(id).orElse(null);
    }

    // ---------- 4️⃣ Shortest path calculation using Dijkstra ----------
    public double calculateShortestDistance(String from, String to) {
        List<Edge> edges = getAllEdges();

        // Build adjacency map
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for (Edge e : edges) {
            graph.putIfAbsent(e.getFromLocation(), new HashMap<>());
            graph.putIfAbsent(e.getToLocation(), new HashMap<>());

            graph.get(e.getFromLocation()).put(e.getToLocation(), e.getDistanceInKm());
            graph.get(e.getToLocation()).put(e.getFromLocation(), e.getDistanceInKm()); // undirected
        }

        // Dijkstra algorithm
        Map<String, Double> distances = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Map.Entry<String, Double>> pq = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));

        for (String node : graph.keySet()) distances.put(node, Double.MAX_VALUE);
        distances.put(from, 0.0);
        pq.add(new AbstractMap.SimpleEntry<>(from, 0.0));

        while (!pq.isEmpty()) {
            Map.Entry<String, Double> current = pq.poll();
            String currentNode = current.getKey();
            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            Map<String, Double> neighbors = graph.getOrDefault(currentNode, new HashMap<>());
            for (Map.Entry<String, Double> neighbor : neighbors.entrySet()) {
                String neighborNode = neighbor.getKey();
                double newDist = distances.get(currentNode) + neighbor.getValue();
                if (newDist < distances.getOrDefault(neighborNode, Double.MAX_VALUE)) {
                    distances.put(neighborNode, newDist);
                    pq.add(new AbstractMap.SimpleEntry<>(neighborNode, newDist));
                }
            }
        }

        return distances.getOrDefault(to, -1.0); // -1 means no path found
    }
}
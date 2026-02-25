package com.example.routing_graph.service;

import com.example.routing_graph.model.Edge;
import com.example.routing_graph.repository.EdgeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EdgeServiceTest {

    @Mock
    private EdgeRepository edgeRepository;

    @InjectMocks
    private EdgeService edgeService;

    public EdgeServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEdge() {
        Edge edge = new Edge("A", "B", 100.0);
        when(edgeRepository.save(edge)).thenReturn(edge);

        Edge saved = edgeService.saveEdge(edge);
        assertNotNull(saved);
        assertEquals("A", saved.getFromLocation());
        verify(edgeRepository, times(1)).save(edge);
    }

    @Test
    void testGetEdgeById() {
        Edge edge = new Edge("A", "B", 100.0);
        edge.setId(1L);
        when(edgeRepository.findById(1L)).thenReturn(Optional.of(edge));

        Edge result = edgeService.getEdgeById(1L);
        assertEquals("B", result.getToLocation());
        verify(edgeRepository, times(1)).findById(1L);
    }
}

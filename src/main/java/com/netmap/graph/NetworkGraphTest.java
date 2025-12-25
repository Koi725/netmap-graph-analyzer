package com.netmap.graph;

import com.netmap.core.domain.*;

import java.util.List;

public class NetworkGraphTest {
    
    public static void main(String[] args) {
        NetworkGraphTest test = new NetworkGraphTest();
        test.runAllTests();
    }
    
    public void runAllTests() {
        System.out.println("Running NetworkGraph Tests...\n");
        
        testAddNode();
        testAddEdge();
        testShortestPath();
        testConnectivity();
        
        System.out.println("\n✓ All NetworkGraph tests passed!");
    }
    
    private void testAddNode() {
        System.out.println("Test: Add Node");
        
        NetworkGraph graph = new NetworkGraph();
        
        Node node1 = Node.builder()
            .id("node1")
            .name("Test Node")
            .ipAddress("192.168.1.1")
            .type(NodeType.SERVER)
            .build();
        
        graph.addNode(node1);
        
        assertEqual(1, graph.getNodeCount(), "Should have 1 node");
        assertTrue(graph.getNode("node1").isPresent(), "Node should exist");
        
        System.out.println("  ✓ Passed\n");
    }
    
    private void testAddEdge() {
        System.out.println("Test: Add Edge");
        
        NetworkGraph graph = new NetworkGraph();
        
        Node node1 = createTestNode("n1", "192.168.1.1");
        Node node2 = createTestNode("n2", "192.168.1.2");
        
        graph.addNode(node1);
        graph.addNode(node2);
        
        Edge edge = Edge.builder()
            .sourceId("n1")
            .targetId("n2")
            .bandwidth(100)
            .latency(10)
            .connectionType(ConnectionType.ETHERNET)
            .build();
        
        graph.addEdge(edge);
        
        assertEqual(1, graph.getEdgeCount(), "Should have 1 edge");
        
        System.out.println("  ✓ Passed\n");
    }
    
    private void testShortestPath() {
        System.out.println("Test: Shortest Path (BFS)");
        
        NetworkGraph graph = new NetworkGraph();
        
        Node n1 = createTestNode("n1", "192.168.1.1");
        Node n2 = createTestNode("n2", "192.168.1.2");
        Node n3 = createTestNode("n3", "192.168.1.3");
        
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        
        graph.addEdge(createTestEdge("n1", "n2"));
        graph.addEdge(createTestEdge("n2", "n3"));
        
        List<String> path = graph.findShortestPath("n1", "n3");
        
        assertEqual(3, path.size(), "Path should have 3 nodes");
        assertEqual("n1", path.get(0), "First should be n1");
        assertEqual("n3", path.get(2), "Last should be n3");
        
        System.out.println("  ✓ Passed\n");
    }
    
    private void testConnectivity() {
        System.out.println("Test: Connectivity Check (DFS)");
        
        NetworkGraph graph = new NetworkGraph();
        
        Node n1 = createTestNode("n1", "192.168.1.1");
        Node n2 = createTestNode("n2", "192.168.1.2");
        Node n3 = createTestNode("n3", "192.168.1.3");
        
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        
        graph.addEdge(createTestEdge("n1", "n2"));
        
        assertTrue(graph.hasPath("n1", "n2"), "Should have path n1 to n2");
        assertFalse(graph.hasPath("n1", "n3"), "Should not have path n1 to n3");
        
        System.out.println("  ✓ Passed\n");
    }
    
    private Node createTestNode(String id, String ip) {
        return Node.builder()
            .id(id)
            .name("Test-" + id)
            .ipAddress(ip)
            .type(NodeType.SERVER)
            .build();
    }
    
    private Edge createTestEdge(String from, String to) {
        return Edge.builder()
            .sourceId(from)
            .targetId(to)
            .bandwidth(100)
            .latency(10)
            .connectionType(ConnectionType.ETHERNET)
            .build();
    }
    
    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
    
    private void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
    
    private void assertEqual(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", Got: " + actual);
        }
    }
    
    private void assertEqual(String expected, String actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: " + expected + ", Got: " + actual);
        }
    }
}
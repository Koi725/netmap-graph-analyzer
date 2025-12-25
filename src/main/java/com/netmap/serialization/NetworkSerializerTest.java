package com.netmap.serialization;

import com.netmap.core.domain.*;
import com.netmap.graph.NetworkGraph;

public class NetworkSerializerTest {
    
    public static void main(String[] args) {
        NetworkSerializerTest test = new NetworkSerializerTest();
        test.runAllTests();
    }
    
    public void runAllTests() {
        System.out.println("Running NetworkSerializer Tests...\n");
        
        testSerialization();
        testDeserialization();
        
        System.out.println("\n✓ All Serialization tests passed!");
    }
    
    private void testSerialization() {
        System.out.println("Test: Serialize Network");
        
        NetworkGraph graph = new NetworkGraph();
        
        Node node = Node.builder()
            .id("test1")
            .name("Test Node")
            .ipAddress("192.168.1.1")
            .type(NodeType.SERVER)
            .build();
        
        graph.addNode(node);
        
        try {
            NetworkSerializer.serialize(graph, "test_network");
            assertTrue(NetworkSerializer.fileExists("test_network"), "File should exist");
            System.out.println("  ✓ Passed\n");
        } catch (Exception e) {
            throw new AssertionError("Serialization failed: " + e.getMessage());
        }
    }
    
    private void testDeserialization() {
        System.out.println("Test: Deserialize Network");
        
        try {
            NetworkGraph loaded = NetworkSerializer.deserialize("test_network");
            assertEqual(1, loaded.getNodeCount(), "Should have 1 node");
            
            NetworkSerializer.deleteSerializedFile("test_network");
            
            System.out.println("  ✓ Passed\n");
        } catch (Exception e) {
            throw new AssertionError("Deserialization failed: " + e.getMessage());
        }
    }
    
    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
    
    private void assertEqual(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", Got: " + actual);
        }
    }
}
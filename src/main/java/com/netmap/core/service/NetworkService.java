package com.netmap.core.service;

import com.netmap.core.domain.Edge;
import com.netmap.core.domain.Node;
import com.netmap.graph.NetworkGraph;
import com.netmap.regex.NetworkValidator;
import com.netmap.serialization.JsonExporter;
import com.netmap.serialization.NetworkSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class NetworkService {
    
    private final NetworkGraph graph;
    
    public NetworkService() {
        this.graph = new NetworkGraph();
    }
    
    public NetworkService(NetworkGraph graph) {
        this.graph = Objects.requireNonNull(graph);
    }
    
    public void addNode(Node node) {
        Objects.requireNonNull(node, "Node cannot be null");
        
        if (!NetworkValidator.isValidIPv4(node.getIpAddress())) {
            throw new IllegalArgumentException("Invalid IP address: " + node.getIpAddress());
        }
        
        graph.addNode(node);
    }
    
    public void addEdge(Edge edge) {
        Objects.requireNonNull(edge, "Edge cannot be null");
        graph.addEdge(edge);
    }
    
    public Optional<Node> findNode(String nodeId) {
        return graph.getNode(nodeId);
    }
    
    public List<Node> getAllNodes() {
        return graph.getAllNodes();
    }
    
    public List<Edge> getNodeConnections(String nodeId) {
        return graph.getEdges(nodeId);
    }
    
    public boolean isConnected(String sourceId, String targetId) {
        return graph.hasPath(sourceId, targetId);
    }
    
    public List<String> findShortestPath(String sourceId, String targetId) {
        return graph.findShortestPath(sourceId, targetId);
    }
    
    public int getNodeCount() {
        return graph.getNodeCount();
    }
    
    public int getConnectionCount() {
        return graph.getEdgeCount();
    }
    
    public int getNodeDegree(String nodeId) {
        return graph.getNodeDegree(nodeId);
    }
    
    public void saveNetwork(String filename) throws IOException {
        NetworkSerializer.serialize(graph, filename);
    }
    
    public void exportToJson(String filename) throws IOException {
        JsonExporter.exportToJson(graph, filename);
    }
    
    public static NetworkService loadNetwork(String filename) 
            throws IOException, ClassNotFoundException {
        NetworkGraph loadedGraph = NetworkSerializer.deserialize(filename);
        return new NetworkService(loadedGraph);
    }
    
    public NetworkGraph getGraph() {
        return graph;
    }
}
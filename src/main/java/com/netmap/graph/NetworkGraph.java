package com.netmap.graph;

import com.netmap.core.domain.Edge;
import com.netmap.core.domain.Node;

import java.io.Serializable;
import java.util.*;

public class NetworkGraph implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final Map<String, Node> nodes;
    private final Map<String, List<Edge>> adjacencyList;
    
    public NetworkGraph() {
        this.nodes = new HashMap<>();
        this.adjacencyList = new HashMap<>();
    }
    
    public void addNode(Node node) {
        Objects.requireNonNull(node, "Node cannot be null");
        nodes.put(node.getId(), node);
        adjacencyList.putIfAbsent(node.getId(), new ArrayList<>());
    }
    
    public void addEdge(Edge edge) {
        Objects.requireNonNull(edge, "Edge cannot be null");
        
        if (!nodes.containsKey(edge.getSourceId())) {
            throw new IllegalArgumentException("Source node not found: " + edge.getSourceId());
        }
        if (!nodes.containsKey(edge.getTargetId())) {
            throw new IllegalArgumentException("Target node not found: " + edge.getTargetId());
        }
        
        adjacencyList.get(edge.getSourceId()).add(edge);
    }
    
    public Optional<Node> getNode(String nodeId) {
        return Optional.ofNullable(nodes.get(nodeId));
    }
    
    public List<Edge> getEdges(String nodeId) {
        return new ArrayList<>(adjacencyList.getOrDefault(nodeId, Collections.emptyList()));
    }
    
    public List<Node> getAllNodes() {
        return new ArrayList<>(nodes.values());
    }
    
    public int getNodeCount() {
        return nodes.size();
    }
    
    public int getEdgeCount() {
        return adjacencyList.values().stream()
            .mapToInt(List::size)
            .sum();
    }
    
    public List<Node> getNeighbors(String nodeId) {
        List<Node> neighbors = new ArrayList<>();
        List<Edge> edges = adjacencyList.getOrDefault(nodeId, Collections.emptyList());
        
        for (Edge edge : edges) {
            nodes.get(edge.getTargetId()).ifPresent(neighbors::add);
        }
        
        return neighbors;
    }
    
    public boolean hasPath(String sourceId, String targetId) {
        if (!nodes.containsKey(sourceId) || !nodes.containsKey(targetId)) {
            return false;
        }
        
        Set<String> visited = new HashSet<>();
        return dfsHasPath(sourceId, targetId, visited);
    }
    
    private boolean dfsHasPath(String current, String target, Set<String> visited) {
        if (current.equals(target)) return true;
        if (visited.contains(current)) return false;
        
        visited.add(current);
        
        for (Edge edge : adjacencyList.getOrDefault(current, Collections.emptyList())) {
            if (dfsHasPath(edge.getTargetId(), target, visited)) {
                return true;
            }
        }
        
        return false;
    }
    
    public List<String> findShortestPath(String sourceId, String targetId) {
        if (!nodes.containsKey(sourceId) || !nodes.containsKey(targetId)) {
            return Collections.emptyList();
        }
        
        Map<String, String> previous = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.add(sourceId);
        visited.add(sourceId);
        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            
            if (current.equals(targetId)) {
                return reconstructPath(previous, sourceId, targetId);
            }
            
            for (Edge edge : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                String neighbor = edge.getTargetId();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        
        return Collections.emptyList();
    }
    
    private List<String> reconstructPath(Map<String, String> previous, String source, String target) {
        List<String> path = new ArrayList<>();
        String current = target;
        
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }
        
        return path.get(0).equals(source) ? path : Collections.emptyList();
    }
    
    public int getNodeDegree(String nodeId) {
        return adjacencyList.getOrDefault(nodeId, Collections.emptyList()).size();
    }
    
    public void clear() {
        nodes.clear();
        adjacencyList.clear();
    }
}
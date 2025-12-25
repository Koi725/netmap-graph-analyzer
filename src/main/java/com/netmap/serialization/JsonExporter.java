package com.netmap.serialization;

import com.netmap.core.domain.Edge;
import com.netmap.core.domain.Node;
import com.netmap.graph.NetworkGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonExporter {
    
    public static void exportToJson(NetworkGraph graph, String filename) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"nodes\": [\n");
        
        List<Node> nodes = graph.getAllNodes();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            json.append("    {\n");
            json.append(String.format("      \"id\": \"%s\",\n", node.getId()));
            json.append(String.format("      \"name\": \"%s\",\n", node.getName()));
            json.append(String.format("      \"ip\": \"%s\",\n", node.getIpAddress()));
            json.append(String.format("      \"type\": \"%s\"\n", node.getType()));
            json.append("    }");
            if (i < nodes.size() - 1) json.append(",");
            json.append("\n");
        }
        
        json.append("  ],\n");
        json.append("  \"edges\": [\n");
        
        List<Edge> allEdges = nodes.stream()
            .flatMap(n -> graph.getEdges(n.getId()).stream())
            .toList();
        
        for (int i = 0; i < allEdges.size(); i++) {
            Edge edge = allEdges.get(i);
            json.append("    {\n");
            json.append(String.format("      \"source\": \"%s\",\n", edge.getSourceId()));
            json.append(String.format("      \"target\": \"%s\",\n", edge.getTargetId()));
            json.append(String.format("      \"bandwidth\": %d,\n", edge.getBandwidth()));
            json.append(String.format("      \"latency\": %d,\n", edge.getLatency()));
            json.append(String.format("      \"type\": \"%s\"\n", edge.getConnectionType()));
            json.append("    }");
            if (i < allEdges.size() - 1) json.append(",");
            json.append("\n");
        }
        
        json.append("  ]\n");
        json.append("}\n");
        
        String filepath = "data/" + filename;
        if (!filepath.endsWith(".json")) {
            filepath += ".json";
        }
        
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(json.toString());
        }
    }
}
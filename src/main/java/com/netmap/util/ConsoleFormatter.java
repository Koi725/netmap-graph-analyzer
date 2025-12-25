package com.netmap.util;

import com.netmap.core.domain.Edge;
import com.netmap.core.domain.Node;

import java.util.List;

public final class ConsoleFormatter {
    
    private static final String LINE = "=".repeat(90);
    private static final String SEPARATOR = "-".repeat(90);
    
    private ConsoleFormatter() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    public static void printHeader(String title) {
        System.out.println("\n" + LINE);
        System.out.println(centerText(title, 90));
        System.out.println(LINE);
    }
    
    public static void printSection(String title) {
        System.out.println("\n" + title);
        System.out.println(SEPARATOR);
    }
    
    public static void printNode(Node node) {
        System.out.printf("%-15s: %s%n", "ID", node.getId());
        System.out.printf("%-15s: %s%n", "Name", node.getName());
        System.out.printf("%-15s: %s%n", "IP Address", node.getIpAddress());
        System.out.printf("%-15s: %s%n", "Type", node.getType().getDisplayName());
        System.out.println(SEPARATOR);
    }
    
    public static void printNodeList(List<Node> nodes, int limit) {
        System.out.printf("%-20s %-25s %-20s %-15s%n",
            "ID", "Name", "IP Address", "Type");
        System.out.println(SEPARATOR);
        
        int count = Math.min(nodes.size(), limit);
        for (int i = 0; i < count; i++) {
            Node n = nodes.get(i);
            System.out.printf("%-20s %-25s %-20s %-15s%n",
                truncate(n.getId(), 18),
                truncate(n.getName(), 23),
                n.getIpAddress(),
                n.getType().name());
        }
        
        if (nodes.size() > limit) {
            System.out.printf("%n... and %d more nodes%n", nodes.size() - limit);
        }
    }
    
    public static void printEdge(Edge edge) {
        System.out.printf("%s → %s [%dMbps, %dms, %s]%n",
            edge.getSourceId(),
            edge.getTargetId(),
            edge.getBandwidth(),
            edge.getLatency(),
            edge.getConnectionType());
    }
    
    public static void printPath(List<String> path) {
        if (path.isEmpty()) {
            System.out.println("No path found");
            return;
        }
        System.out.println(String.join(" → ", path));
    }
    
    public static void printStatistic(String label, Object value) {
        System.out.printf("%-40s: %s%n", label, value);
    }
    
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
    
    private static String truncate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() <= maxLength ? text : text.substring(0, maxLength - 3) + "...";
    }
}
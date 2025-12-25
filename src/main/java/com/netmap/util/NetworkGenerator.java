package com.netmap.util;

import com.netmap.core.domain.*;

import java.util.Random;

public final class NetworkGenerator {
    
    private static final Random RANDOM = new Random();
    
    private static final String[] SERVER_NAMES = {
        "web-server-01", "db-server-01", "app-server-01", 
        "cache-server-01", "api-server-01"
    };
    
    private static final String[] ROUTER_NAMES = {
        "router-core-01", "router-edge-01", "router-vpn-01"
    };
    
    private static final String[] CLIENT_NAMES = {
        "workstation-01", "laptop-alice", "laptop-bob", "desktop-charlie"
    };
    
    private NetworkGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    public static Node generateRandomNode(NodeType type) {
        String id = generateNodeId(type);
        String name = generateNodeName(type);
        String ip = generateRandomIP();
        
        return Node.builder()
            .id(id)
            .name(name)
            .ipAddress(ip)
            .type(type)
            .build();
    }
    
    public static Node generateServer() {
        return generateRandomNode(NodeType.SERVER);
    }
    
    public static Node generateRouter() {
        return generateRandomNode(NodeType.ROUTER);
    }
    
    public static Node generateClient() {
        return generateRandomNode(NodeType.CLIENT);
    }
    
    public static Edge generateRandomEdge(String sourceId, String targetId) {
        int bandwidth = (RANDOM.nextInt(10) + 1) * 100;
        int latency = RANDOM.nextInt(50) + 1;
        ConnectionType type = ConnectionType.values()[RANDOM.nextInt(ConnectionType.values().length)];
        
        return Edge.builder()
            .sourceId(sourceId)
            .targetId(targetId)
            .bandwidth(bandwidth)
            .latency(latency)
            .connectionType(type)
            .build();
    }
    
    private static String generateNodeId(NodeType type) {
        return type.name().toLowerCase() + "-" + 
               String.format("%04d", RANDOM.nextInt(10000));
    }
    
    private static String generateNodeName(NodeType type) {
        return switch (type) {
            case SERVER -> SERVER_NAMES[RANDOM.nextInt(SERVER_NAMES.length)];
            case ROUTER -> ROUTER_NAMES[RANDOM.nextInt(ROUTER_NAMES.length)];
            case CLIENT -> CLIENT_NAMES[RANDOM.nextInt(CLIENT_NAMES.length)];
            default -> "node-" + RANDOM.nextInt(100);
        };
    }
    
    private static String generateRandomIP() {
        return String.format("192.168.%d.%d",
            RANDOM.nextInt(256),
            RANDOM.nextInt(256));
    }
}
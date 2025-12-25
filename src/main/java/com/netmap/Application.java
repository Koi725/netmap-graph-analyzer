package com.netmap;

import com.netmap.core.domain.*;
import com.netmap.core.service.NetworkService;
import com.netmap.regex.NetworkValidator;
import com.netmap.util.ConsoleFormatter;
import com.netmap.util.Logger;
import com.netmap.util.NetworkGenerator;

import java.util.List;

public class Application {
    
    private final NetworkService networkService;
    
    public Application() {
        Logger.info("Initializing NetMap Application");
        this.networkService = new NetworkService();
        Logger.success("Application initialized");
    }
    
    public static void main(String[] args) {
        Logger.info("=== NETMAP APPLICATION STARTED ===");
        
        Application app = new Application();
        
        try {
            app.run();
        } catch (Exception e) {
            Logger.error("Application failed", e);
        } finally {
            Logger.info("=== NETMAP APPLICATION ENDED ===");
            Logger.close();
        }
    }
    
    public void run() {
        ConsoleFormatter.printHeader("NETMAP - NETWORK GRAPH ANALYZER");
        
        try {
            demonstrateRegex();
            demonstrateGraphOperations();
            demonstrateSerialization();
            displayStatistics();
            
            ConsoleFormatter.printSection("✓ ALL DEMONSTRATIONS COMPLETED");
            Logger.success("All demonstrations completed");
            
        } catch (Exception e) {
            Logger.error("Error during demonstration", e);
            throw new RuntimeException(e);
        }
    }
    
    private void demonstrateRegex() {
        Logger.info("=== REGEX DEMONSTRATION START ===");
        ConsoleFormatter.printSection("1. REGEX - NETWORK VALIDATION");
        
        String[] testIPs = {
            "192.168.1.1",
            "10.0.0.255",
            "256.1.1.1",
            "172.16.0.1"
        };
        
        Logger.info("Testing IP address validation");
        System.out.println("\nIP Address Validation:");
        for (String ip : testIPs) {
            boolean valid = NetworkValidator.isValidIPv4(ip);
            System.out.printf("  %-20s → %s%n", ip, valid ? "✓ Valid" : "✗ Invalid");
            Logger.info("IP " + ip + " validation: " + valid);
        }
        
        String[] testEmails = {
            "admin@network.com",
            "user.name@company.co.uk",
            "invalid@email",
            "not-an-email"
        };
        
        System.out.println("\nEmail Validation:");
        for (String email : testEmails) {
            boolean valid = NetworkValidator.isValidEmail(email);
            System.out.printf("  %-30s → %s%n", email, valid ? "✓ Valid" : "✗ Invalid");
        }
        
        String logText = "Server 192.168.1.100 connected from 10.0.0.50";
        List<String> extractedIPs = NetworkValidator.extractIPv4Addresses(logText);
        System.out.println("\nExtracted IPs from log:");
        System.out.println("  Text: " + logText);
        System.out.println("  Found: " + extractedIPs);
        Logger.info("Extracted IPs: " + extractedIPs);
        
        Logger.info("=== REGEX DEMONSTRATION END ===");
    }
    
    private void demonstrateGraphOperations() {
        Logger.info("=== GRAPH DEMONSTRATION START ===");
        ConsoleFormatter.printSection("2. GRAPH - NETWORK TOPOLOGY");
        
        Logger.info("Building network topology");
        System.out.println("\nBuilding network with 10 nodes...");
        
        Node server1 = NetworkGenerator.generateServer();
        Node server2 = NetworkGenerator.generateServer();
        Node router1 = NetworkGenerator.generateRouter();
        Node router2 = NetworkGenerator.generateRouter();
        Node client1 = NetworkGenerator.generateClient();
        Node client2 = NetworkGenerator.generateClient();
        Node client3 = NetworkGenerator.generateClient();
        Node client4 = NetworkGenerator.generateClient();
        Node firewall = Node.builder()
            .id("firewall-01")
            .name("main-firewall")
            .ipAddress("192.168.1.1")
            .type(NodeType.FIREWALL)
            .build();
        Node switchNode = Node.builder()
            .id("switch-01")
            .name("core-switch")
            .ipAddress("192.168.1.2")
            .type(NodeType.SWITCH)
            .build();
        
        networkService.addNode(server1);
        networkService.addNode(server2);
        networkService.addNode(router1);
        networkService.addNode(router2);
        networkService.addNode(client1);
        networkService.addNode(client2);
        networkService.addNode(client3);
        networkService.addNode(client4);
        networkService.addNode(firewall);
        networkService.addNode(switchNode);
        
        Logger.success("Created 10 network nodes");
        
        System.out.println("\nAdding connections...");
        networkService.addEdge(NetworkGenerator.generateRandomEdge(firewall.getId(), router1.getId()));
        networkService.addEdge(NetworkGenerator.generateRandomEdge(router1.getId(), switchNode.getId()));
        networkService.addEdge(NetworkGenerator.generateRandomEdge(switchNode.getId(), server1.getId()));
        networkService.addEdge(NetworkGenerator.generateRandomEdge(switchNode.getId(), server2.getId()));
        networkService.addEdge(NetworkGenerator.generateRandomEdge(router1.getId(), router2.getId()));
        networkService.addEdge(NetworkGenerator.generateRandomEdge(router2.getId(), client1.getId()));
        networkService.addEdge(NetworkGenerator.generateRandomEdge(router2.getId(), client2.getId()));
        networkService.addEdge(NetworkGenerator.generateRandomEdge(switchNode.getId(), client3.getId()));
        networkService.addEdge(NetworkGenerator.generateRandomEdge(switchNode.getId(), client4.getId()));
        
        Logger.success("Added 9 network connections");
        
        System.out.println("\nNetwork Nodes:");
        ConsoleFormatter.printNodeList(networkService.getAllNodes(), 5);
        
        System.out.println("\nPath Finding (BFS Algorithm):");
        List<String> path = networkService.findShortestPath(firewall.getId(), server1.getId());
        System.out.print("  Shortest path from Firewall to Server1: ");
        ConsoleFormatter.printPath(path);
        Logger.info("Shortest path: " + path);
        
        System.out.println("\nConnectivity Check (DFS Algorithm):");
        boolean connected = networkService.isConnected(client1.getId(), server2.getId());
        System.out.printf("  Client1 can reach Server2: %s%n", connected ? "✓ Yes" : "✗ No");
        Logger.info("Connectivity check: " + connected);
        
        Logger.info("=== GRAPH DEMONSTRATION END ===");
    }
    
    private void demonstrateSerialization() {
        Logger.info("=== SERIALIZATION DEMONSTRATION START ===");
        ConsoleFormatter.printSection("3. SERIALIZATION - SAVE & LOAD");
        
        try {
            String filename = "network_backup";
            
            System.out.println("\nSaving network to disk...");
            networkService.saveNetwork(filename);
            Logger.success("Network serialized to " + filename + ".ser");
            System.out.println("  ✓ Saved to data/" + filename + ".ser");
            
            System.out.println("\nExporting to JSON...");
            networkService.exportToJson("network_export");
            Logger.success("Network exported to JSON");
            System.out.println("  ✓ Exported to data/network_export.json");
            
            System.out.println("\nLoading network from disk...");
            NetworkService loaded = NetworkService.loadNetwork(filename);
            Logger.success("Network deserialized successfully");
            System.out.println("  ✓ Loaded " + loaded.getNodeCount() + " nodes");
            System.out.println("  ✓ Loaded " + loaded.getConnectionCount() + " connections");
            
            System.out.println("\nVerifying loaded data matches original:");
            boolean matches = loaded.getNodeCount() == networkService.getNodeCount() &&
                            loaded.getConnectionCount() == networkService.getConnectionCount();
            System.out.println("  " + (matches ? "✓ Data integrity verified" : "✗ Data mismatch"));
            Logger.info("Data integrity check: " + matches);
            
        } catch (Exception e) {
            Logger.error("Serialization failed", e);
            System.err.println("  ✗ Serialization error: " + e.getMessage());
        }
        
        Logger.info("=== SERIALIZATION DEMONSTRATION END ===");
    }
    
    private void displayStatistics() {
        Logger.info("=== STATISTICS DISPLAY START ===");
        ConsoleFormatter.printSection("4. NETWORK STATISTICS");
        
        ConsoleFormatter.printStatistic("Total nodes", networkService.getNodeCount());
        ConsoleFormatter.printStatistic("Total connections", networkService.getConnectionCount());
        
        long servers = networkService.getAllNodes().stream()
            .filter(n -> n.getType() == NodeType.SERVER)
            .count();
        long routers = networkService.getAllNodes().stream()
            .filter(n -> n.getType() == NodeType.ROUTER)
            .count();
        long clients = networkService.getAllNodes().stream()
            .filter(n -> n.getType() == NodeType.CLIENT)
            .count();
        
        ConsoleFormatter.printStatistic("Servers", servers);
        ConsoleFormatter.printStatistic("Routers", routers);
        ConsoleFormatter.printStatistic("Clients", clients);
        
        Logger.info("Statistics - Nodes: " + networkService.getNodeCount() + 
                   ", Connections: " + networkService.getConnectionCount());
        Logger.info("=== STATISTICS DISPLAY END ===");
    }
}
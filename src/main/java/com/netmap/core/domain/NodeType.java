package com.netmap.core.domain;

public enum NodeType {
    SERVER("Server"),
    ROUTER("Router"),
    SWITCH("Switch"),
    CLIENT("Client"),
    FIREWALL("Firewall");
    
    private final String displayName;
    
    NodeType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
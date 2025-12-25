package com.netmap.core.domain;

public enum ConnectionType {
    ETHERNET("Ethernet"),
    FIBER("Fiber Optic"),
    WIRELESS("Wireless"),
    VPN("VPN");
    
    private final String displayName;
    
    ConnectionType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
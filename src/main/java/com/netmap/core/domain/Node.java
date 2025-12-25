package com.netmap.core.domain;

import java.io.Serializable;
import java.util.Objects;

public class Node implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final String id;
    private final String name;
    private final String ipAddress;
    private final NodeType type;
    
    private Node(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.ipAddress = builder.ipAddress;
        this.type = builder.type;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public NodeType getType() {
        return type;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return id.equals(node.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Node[id=%s, name=%s, ip=%s, type=%s]", 
            id, name, ipAddress, type);
    }
    
    public static class Builder {
        private String id;
        private String name;
        private String ipAddress;
        private NodeType type;
        
        public Builder id(String id) {
            this.id = Objects.requireNonNull(id);
            return this;
        }
        
        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }
        
        public Builder ipAddress(String ipAddress) {
            this.ipAddress = Objects.requireNonNull(ipAddress);
            return this;
        }
        
        public Builder type(NodeType type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }
        
        public Node build() {
            Objects.requireNonNull(id, "ID required");
            Objects.requireNonNull(name, "Name required");
            Objects.requireNonNull(ipAddress, "IP address required");
            Objects.requireNonNull(type, "Type required");
            return new Node(this);
        }
    }
}
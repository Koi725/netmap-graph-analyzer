package com.netmap.core.domain;

import java.io.Serializable;
import java.util.Objects;

public class Edge implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final String sourceId;
    private final String targetId;
    private final int bandwidth;
    private final int latency;
    private final ConnectionType connectionType;
    
    private Edge(Builder builder) {
        this.sourceId = builder.sourceId;
        this.targetId = builder.targetId;
        this.bandwidth = builder.bandwidth;
        this.latency = builder.latency;
        this.connectionType = builder.connectionType;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public String getSourceId() {
        return sourceId;
    }
    
    public String getTargetId() {
        return targetId;
    }
    
    public int getBandwidth() {
        return bandwidth;
    }
    
    public int getLatency() {
        return latency;
    }
    
    public ConnectionType getConnectionType() {
        return connectionType;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return sourceId.equals(edge.sourceId) && targetId.equals(edge.targetId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(sourceId, targetId);
    }
    
    @Override
    public String toString() {
        return String.format("Edge[%s -> %s, %dMbps, %dms]", 
            sourceId, targetId, bandwidth, latency);
    }
    
    public static class Builder {
        private String sourceId;
        private String targetId;
        private int bandwidth;
        private int latency;
        private ConnectionType connectionType;
        
        public Builder sourceId(String sourceId) {
            this.sourceId = Objects.requireNonNull(sourceId);
            return this;
        }
        
        public Builder targetId(String targetId) {
            this.targetId = Objects.requireNonNull(targetId);
            return this;
        }
        
        public Builder bandwidth(int bandwidth) {
            if (bandwidth <= 0) throw new IllegalArgumentException("Bandwidth must be positive");
            this.bandwidth = bandwidth;
            return this;
        }
        
        public Builder latency(int latency) {
            if (latency < 0) throw new IllegalArgumentException("Latency cannot be negative");
            this.latency = latency;
            return this;
        }
        
        public Builder connectionType(ConnectionType connectionType) {
            this.connectionType = Objects.requireNonNull(connectionType);
            return this;
        }
        
        public Edge build() {
            Objects.requireNonNull(sourceId, "Source ID required");
            Objects.requireNonNull(targetId, "Target ID required");
            Objects.requireNonNull(connectionType, "Connection type required");
            return new Edge(this);
        }
    }
}
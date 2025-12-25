package com.netmap.serialization;

import com.netmap.graph.NetworkGraph;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class NetworkSerializer {
    
    private static final String DEFAULT_DIRECTORY = "data";
    
    public static void serialize(NetworkGraph graph, String filename) throws IOException {
        Objects.requireNonNull(graph, "Graph cannot be null");
        Objects.requireNonNull(filename, "Filename cannot be null");
        
        Path directory = Paths.get(DEFAULT_DIRECTORY);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        
        String filepath = DEFAULT_DIRECTORY + File.separator + filename;
        if (!filepath.endsWith(".ser")) {
            filepath += ".ser";
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filepath))) {
            oos.writeObject(graph);
        }
    }
    
    public static NetworkGraph deserialize(String filename) throws IOException, ClassNotFoundException {
        Objects.requireNonNull(filename, "Filename cannot be null");
        
        String filepath = DEFAULT_DIRECTORY + File.separator + filename;
        if (!filepath.endsWith(".ser")) {
            filepath += ".ser";
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filepath))) {
            return (NetworkGraph) ois.readObject();
        }
    }
    
    public static void serializeToBytes(NetworkGraph graph, OutputStream outputStream) 
            throws IOException {
        Objects.requireNonNull(graph, "Graph cannot be null");
        Objects.requireNonNull(outputStream, "OutputStream cannot be null");
        
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(graph);
        }
    }
    
    public static NetworkGraph deserializeFromBytes(InputStream inputStream) 
            throws IOException, ClassNotFoundException {
        Objects.requireNonNull(inputStream, "InputStream cannot be null");
        
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return (NetworkGraph) ois.readObject();
        }
    }
    
    public static boolean fileExists(String filename) {
        String filepath = DEFAULT_DIRECTORY + File.separator + filename;
        if (!filepath.endsWith(".ser")) {
            filepath += ".ser";
        }
        return Files.exists(Paths.get(filepath));
    }
    
    public static void deleteSerializedFile(String filename) throws IOException {
        String filepath = DEFAULT_DIRECTORY + File.separator + filename;
        if (!filepath.endsWith(".ser")) {
            filepath += ".ser";
        }
        Files.deleteIfExists(Paths.get(filepath));
    }
}
package com.netmap.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Logger {
    
    private static final String LOG_FILE = "logs.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static BufferedWriter writer;
    
    static {
        initializeLogFile();
    }
    
    private Logger() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    private static void initializeLogFile() {
        try {
            File logFile = new File(LOG_FILE);
            if (logFile.exists()) {
                logFile.delete();
            }
            logFile.createNewFile();
            writer = new BufferedWriter(new FileWriter(logFile, true));
            log("INFO", "Logger initialized");
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }
    
    public static void info(String message) {
        log("INFO", message);
        System.out.println("ℹ " + message);
    }
    
    public static void success(String message) {
        log("SUCCESS", message);
        System.out.println("✓ " + message);
    }
    
    public static void warning(String message) {
        log("WARNING", message);
        System.out.println("⚠ " + message);
    }
    
    public static void error(String message) {
        log("ERROR", message);
        System.err.println("✗ " + message);
    }
    
    public static void error(String message, Throwable throwable) {
        log("ERROR", message + " - " + throwable.getMessage());
        System.err.println("✗ " + message);
        throwable.printStackTrace();
        logStackTrace(throwable);
    }
    
    private static synchronized void log(String level, String message) {
        try {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String logLine = String.format("[%s] [%s] %s%n", timestamp, level, message);
            if (writer != null) {
                writer.write(logLine);
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
    
    private static void logStackTrace(Throwable throwable) {
        try {
            writer.write("Stack trace:\n");
            for (StackTraceElement element : throwable.getStackTrace()) {
                writer.write("    at " + element.toString() + "\n");
            }
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to log stack trace");
        }
    }
    
    public static void close() {
        try {
            if (writer != null) {
                log("INFO", "Logger shutdown");
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Failed to close logger");
        }
    }
}
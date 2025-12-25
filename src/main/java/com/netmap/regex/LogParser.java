package com.netmap.regex;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    
    private static final Pattern LOG_ENTRY_PATTERN = Pattern.compile(
        "^\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})\\] " +
        "\\[([A-Z]+)\\] " +
        "\\[([^\\]]+)\\] " +
        "(.+)$"
    );
    
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static class LogEntry {
        private final LocalDateTime timestamp;
        private final String level;
        private final String source;
        private final String message;
        
        public LogEntry(LocalDateTime timestamp, String level, String source, String message) {
            this.timestamp = timestamp;
            this.level = level;
            this.source = source;
            this.message = message;
        }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public String getLevel() { return level; }
        public String getSource() { return source; }
        public String getMessage() { return message; }
        
        @Override
        public String toString() {
            return String.format("[%s] [%s] [%s] %s",
                timestamp.format(TIMESTAMP_FORMATTER), level, source, message);
        }
    }
    
    public static LogEntry parseLogEntry(String logLine) {
        if (logLine == null || logLine.isEmpty()) {
            return null;
        }
        
        Matcher matcher = LOG_ENTRY_PATTERN.matcher(logLine);
        if (!matcher.matches()) {
            return null;
        }
        
        LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), TIMESTAMP_FORMATTER);
        String level = matcher.group(2);
        String source = matcher.group(3);
        String message = matcher.group(4);
        
        return new LogEntry(timestamp, level, source, message);
    }
    
    public static List<LogEntry> parseLogFile(List<String> lines) {
        List<LogEntry> entries = new ArrayList<>();
        for (String line : lines) {
            LogEntry entry = parseLogEntry(line);
            if (entry != null) {
                entries.add(entry);
            }
        }
        return entries;
    }
    
    public static List<LogEntry> filterByLevel(List<LogEntry> entries, String level) {
        return entries.stream()
            .filter(e -> e.getLevel().equalsIgnoreCase(level))
            .toList();
    }
}
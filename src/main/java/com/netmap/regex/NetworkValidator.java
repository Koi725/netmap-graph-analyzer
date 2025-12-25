package com.netmap.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NetworkValidator {
    
    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );
    
    private static final Pattern MAC_ADDRESS_PATTERN = Pattern.compile(
        "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$"
    );
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern HOSTNAME_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(\\.[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
    );
    
    private static final Pattern PORT_PATTERN = Pattern.compile(
        "^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$"
    );
    
    private static final Pattern URL_PATTERN = Pattern.compile(
        "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
        Pattern.CASE_INSENSITIVE
    );
    
    private NetworkValidator() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    public static boolean isValidIPv4(String ip) {
        if (ip == null || ip.isEmpty()) return false;
        return IPV4_PATTERN.matcher(ip).matches();
    }
    
    public static boolean isValidMacAddress(String mac) {
        if (mac == null || mac.isEmpty()) return false;
        return MAC_ADDRESS_PATTERN.matcher(mac).matches();
    }
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidHostname(String hostname) {
        if (hostname == null || hostname.isEmpty()) return false;
        return HOSTNAME_PATTERN.matcher(hostname).matches();
    }
    
    public static boolean isValidPort(String port) {
        if (port == null || port.isEmpty()) return false;
        return PORT_PATTERN.matcher(port).matches();
    }
    
    public static boolean isValidPort(int port) {
        return port > 0 && port <= 65535;
    }
    
    public static boolean isValidURL(String url) {
        if (url == null || url.isEmpty()) return false;
        return URL_PATTERN.matcher(url).matches();
    }
    
    public static List<String> extractIPv4Addresses(String text) {
        List<String> ips = new ArrayList<>();
        Matcher matcher = IPV4_PATTERN.matcher(text);
        while (matcher.find()) {
            ips.add(matcher.group());
        }
        return ips;
    }
    
    public static List<String> extractEmailAddresses(String text) {
        List<String> emails = new ArrayList<>();
        Matcher matcher = EMAIL_PATTERN.matcher(text);
        while (matcher.find()) {
            emails.add(matcher.group());
        }
        return emails;
    }
    
    public static String sanitizeNodeId(String input) {
        if (input == null) return null;
        return input.replaceAll("[^a-zA-Z0-9_-]", "_");
    }
    
    public static boolean containsPrivateIP(String ip) {
        if (!isValidIPv4(ip)) return false;
        
        Pattern privatePattern = Pattern.compile(
            "^(10\\.|172\\.(1[6-9]|2[0-9]|3[0-1])\\.|192\\.168\\.)"
        );
        return privatePattern.matcher(ip).find();
    }
}
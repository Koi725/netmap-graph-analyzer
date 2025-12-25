package com.netmap.regex;

public class NetworkValidatorTest {
    
    public static void main(String[] args) {
        NetworkValidatorTest test = new NetworkValidatorTest();
        test.runAllTests();
    }
    
    public void runAllTests() {
        System.out.println("Running NetworkValidator Tests...\n");
        
        testIPv4Validation();
        testEmailValidation();
        testMacAddressValidation();
        testIPExtraction();
        
        System.out.println("\n✓ All NetworkValidator tests passed!");
    }
    
    private void testIPv4Validation() {
        System.out.println("Test: IPv4 Validation");
        
        assertTrue(NetworkValidator.isValidIPv4("192.168.1.1"), "Valid IP should pass");
        assertTrue(NetworkValidator.isValidIPv4("10.0.0.0"), "Valid IP should pass");
        assertTrue(NetworkValidator.isValidIPv4("255.255.255.255"), "Max IP should pass");
        
        assertFalse(NetworkValidator.isValidIPv4("256.1.1.1"), "256 should fail");
        assertFalse(NetworkValidator.isValidIPv4("192.168.1"), "Incomplete IP should fail");
        assertFalse(NetworkValidator.isValidIPv4("not.an.ip.address"), "Text should fail");
        
        System.out.println("  ✓ Passed\n");
    }
    
    private void testEmailValidation() {
        System.out.println("Test: Email Validation");
        
        assertTrue(NetworkValidator.isValidEmail("user@example.com"), "Valid email");
        assertTrue(NetworkValidator.isValidEmail("admin@network.co.uk"), "Valid email with subdomain");
        
        assertFalse(NetworkValidator.isValidEmail("invalid@"), "Incomplete email");
        assertFalse(NetworkValidator.isValidEmail("@example.com"), "Missing username");
        
        System.out.println("  ✓ Passed\n");
    }
    
    private void testMacAddressValidation() {
        System.out.println("Test: MAC Address Validation");
        
        assertTrue(NetworkValidator.isValidMacAddress("00:1A:2B:3C:4D:5E"), "Valid MAC");
        assertTrue(NetworkValidator.isValidMacAddress("00-1A-2B-3C-4D-5E"), "Valid MAC with dash");
        
        assertFalse(NetworkValidator.isValidMacAddress("00:1A:2B"), "Incomplete MAC");
        assertFalse(NetworkValidator.isValidMacAddress("ZZ:ZZ:ZZ:ZZ:ZZ:ZZ"), "Invalid hex");
        
        System.out.println("  ✓ Passed\n");
    }
    
    private void testIPExtraction() {
        System.out.println("Test: IP Extraction from Text");
        
        String text = "Server 192.168.1.100 connected from 10.0.0.50 at port 8080";
        var ips = NetworkValidator.extractIPv4Addresses(text);
        
        assertEqual(2, ips.size(), "Should extract 2 IPs");
        assertTrue(ips.contains("192.168.1.100"), "Should contain first IP");
        assertTrue(ips.contains("10.0.0.50"), "Should contain second IP");
        
        System.out.println("  ✓ Passed\n");
    }
    
    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
    
    private void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
    
    private void assertEqual(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", Got: " + actual);
        }
    }
}
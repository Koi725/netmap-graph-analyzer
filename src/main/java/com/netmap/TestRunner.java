package com.netmap;

import com.netmap.graph.NetworkGraphTest;
import com.netmap.regex.NetworkValidatorTest;
import com.netmap.serialization.NetworkSerializerTest;
import com.netmap.util.Logger;

public class TestRunner {
    
    public static void main(String[] args) {
        Logger.info("=== TEST SUITE STARTED ===");
        
        System.out.println("================================================================================");
        System.out.println("                            NETMAP TEST SUITE");
        System.out.println("================================================================================\n");
        
        int passed = 0;
        int failed = 0;
        
        try {
            Logger.info("Running NetworkValidatorTest");
            new NetworkValidatorTest().runAllTests();
            passed++;
            Logger.success("NetworkValidatorTest passed");
            
            Logger.info("Running NetworkGraphTest");
            new NetworkGraphTest().runAllTests();
            passed++;
            Logger.success("NetworkGraphTest passed");
            
            Logger.info("Running NetworkSerializerTest");
            new NetworkSerializerTest().runAllTests();
            passed++;
            Logger.success("NetworkSerializerTest passed");
            
        } catch (AssertionError e) {
            failed++;
            Logger.error("Test failed: " + e.getMessage(), e);
            System.err.println("\n✗ TEST FAILED: " + e.getMessage());
        } catch (Exception e) {
            failed++;
            Logger.error("Unexpected test error", e);
            System.err.println("\n✗ UNEXPECTED ERROR: " + e.getMessage());
        }
        
        System.out.println("\n================================================================================");
        System.out.println("RESULTS: Passed: " + passed + " | Failed: " + failed);
        System.out.println("================================================================================\n");
        
        Logger.info("Test results - Passed: " + passed + ", Failed: " + failed);
        Logger.info("=== TEST SUITE ENDED ===");
        Logger.close();
    }
}
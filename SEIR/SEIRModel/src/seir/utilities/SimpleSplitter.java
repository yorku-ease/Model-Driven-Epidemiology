package seir.utilities;

import java.io.File;

/**
 * Simple test to check if we can run Java applications and find the model file
 */
public class SimpleSplitter {
    
    public static void main(String[] args) {
        System.out.println("=== Simple SEIR Model Splitter Test ===");
        
        // Check current working directory
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current directory: " + currentDir);
        
        // Look for covid.seirmodel file
        File modelFile = new File("covid.seirmodel");
        System.out.println("Looking for: " + modelFile.getAbsolutePath());
        
        if (modelFile.exists()) {
            System.out.println("✓ Found covid.seirmodel!");
            System.out.println("File size: " + modelFile.length() + " bytes");
            
            // List all .seirmodel files in directory
            File currentDirFile = new File(".");
            File[] seirFiles = currentDirFile.listFiles((dir, name) -> name.endsWith(".seirmodel"));
            
            if (seirFiles != null && seirFiles.length > 0) {
                System.out.println("\nAll .seirmodel files found:");
                for (File file : seirFiles) {
                    System.out.println("  - " + file.getName());
                }
            }
            
            System.out.println("\n🎉 Test successful! Ready to process the model.");
            
        } else {
            System.out.println("❌ covid.seirmodel not found!");
            System.out.println("Make sure the file is in the same directory as this Java class.");
            
            // List all files in current directory
            File currentDirFile = new File(".");
            File[] allFiles = currentDirFile.listFiles();
            
            if (allFiles != null) {
                System.out.println("\nFiles in current directory:");
                for (File file : allFiles) {
                    if (file.isFile()) {
                        System.out.println("  - " + file.getName());
                    }
                }
            }
        }
        
        System.out.println("\nJava classpath:");
        String classpath = System.getProperty("java.class.path");
        String[] paths = classpath.split(File.pathSeparator);
        for (String path : paths) {
            System.out.println("  - " + path);
        }
    }
}
package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("");
        System.out.println("Recursion:");
        printDirectoryFiles(dir,"");
    }

    public static void printDirectoryFiles(File dir, String indent) {
        File[] files = dir.listFiles();
        Arrays.sort(files);

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(indent + "F: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(indent + "D: " + file.getName());
                    printDirectoryFiles(file,indent + "  ");
                }
            }
        }
    }
}
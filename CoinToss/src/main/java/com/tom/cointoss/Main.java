package com.tom.cointoss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Run: java [input_path] [output_path]");
            }
            String[] sequenceOfCoinToss = readLinesFromFile(args[0]);
            int[] longestSequence = getLongestSequence(sequenceOfCoinToss);

            writeToFile(args[1], prepareResult(longestSequence));
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int[] getLongestSequence(String[] sequence) {
        int maxHeadLength = 0, maxTailLength = 0;
        int currentHeadLength = 0, currentTailLength = 0;
        for (String character : sequence) {
            if ("F".equals(character)) {
                maxTailLength = Math.max(currentTailLength, maxTailLength);
                currentTailLength = 0;
                currentHeadLength++;
            } 
            if("I".equals(character)) {
                maxHeadLength = Math.max(currentHeadLength, maxHeadLength);
                currentHeadLength = 0;
                currentTailLength++;
            }
        }
        return new int[]{maxHeadLength, maxTailLength};
    }

    private static String prepareResult(int[] results) {
        StringBuilder sb = new StringBuilder();
        sb.append(results[0]);
        sb.append("\n");
        sb.append(results[1]);
        return sb.toString();
    }

    public static String[] readLinesFromFile(String pathString) throws IOException {
        try (Scanner sc = new Scanner(new File(pathString))) {
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {                
                String line = sc.nextLine();
                sb.append(line);
            }
            return sb.toString().split("");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Unable to read the file, path: " + pathString);
        }
    }

    public static void writeToFile(String pathString, String content) throws IOException {
        Path path = Paths.get(pathString);
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
            writer.write(content + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Unable to write to file, path: " + pathString);
        }
    }
}

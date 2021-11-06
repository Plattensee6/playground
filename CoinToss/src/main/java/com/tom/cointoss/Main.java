package com.tom.cointoss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final int DEFAULT_QUANTITY = 0;
    private static final int DEFAULT_LENGTH = 0;
    
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

    public static int[] getLongestSequence(final String[] sequence) {
        int currentHeadLength = DEFAULT_LENGTH, currentTailLength = DEFAULT_LENGTH;
        
        // Key: length of sequence, Value: quantity
        Map<Integer, Integer> headSequences = new HashMap<>();
        Map<Integer, Integer> tailSequences = new HashMap<>();

        for (String character : sequence) {
            // HEAD
            if ("F".equals(character)) {
                
                // End of Tail sequence, put length and quantity into tailSequence map
                if (currentTailLength != DEFAULT_LENGTH) {
                    if (tailSequences.containsKey(currentTailLength)) {
                        int quantity = tailSequences.get(currentTailLength) + 1;
                        tailSequences.put(currentTailLength, quantity);
                    } else {
                        tailSequences.put(currentTailLength, 1);
                    }
                    // reset tail sequence's length
                    currentTailLength = DEFAULT_LENGTH;
                }
                currentHeadLength++;
            }
            // TAIL
            if ("I".equals(character)) {
                // End of Head sequence, put length and quantity into headSequence map
                if (currentHeadLength != DEFAULT_LENGTH) {
                    
                    // If sequence already exists increment its quantity.
                    if (headSequences.containsKey(currentHeadLength)) {
                        int quantity = headSequences.get(currentHeadLength) + 1;
                        headSequences.put(currentHeadLength, quantity);
                    } else {
                        headSequences.put(currentHeadLength, 1);
                    }
                    // reset Head sequence's length
                    currentHeadLength = DEFAULT_LENGTH;
                }
                currentTailLength++;
            }
        }
        // IFFIIIFFIFIII
        return new int[]{getMaxLength(headSequences), getMaxLength(tailSequences)};
    }

    private static int getMaxLength(Map<Integer, Integer> map) {
        int maxQuantity = DEFAULT_QUANTITY;
        int maxLength = DEFAULT_LENGTH;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int length = entry.getKey();
            int quantity = entry.getValue();
            if (maxQuantity < quantity) {
                maxQuantity = quantity;
                maxLength = length;
            }
            if (maxLength == quantity) {
                maxLength = Math.max(maxLength, length);
            }
        }
        return maxLength;
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
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Unable to write to file, path: " + pathString);
        }
    }
}

package com.tom.familytree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
//          int[][] relations = {
//              {1,2},
//              {2,4},
//              {1,3},
//              {3,5},
//              {5,7},
//              {5,8},
//              {5,9},
//              {3,6},
//              {6,10}
//          };
          HashMap<Integer, Integer> relations = new HashMap<>();
          
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static HashMap<Integer, List<Integer>> readRelationsFromFile(String pathString) throws IOException {
        try (Scanner sc = new Scanner(new File(pathString))) {
            HashMap<Integer, List<Integer>> map = new HashMap<>();
            while (sc.hasNext()) {
                String[] persons = sc.nextLine().split(" ");
                
                Integer father = Integer.parseInt(persons[0]);
                Integer child = Integer.parseInt(persons[1]);
                if (map.containsKey(father)) {
                    map.get(father).add(child);
                } else {
                    List<Integer> children = new ArrayList<>();
                    children.add(child);
                    map.put(father, children);
                }
                
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Unable to read the file, path: " + pathString);
        }
    }
}

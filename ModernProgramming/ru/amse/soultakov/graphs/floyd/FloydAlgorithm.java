package ru.amse.soultakov.graphs.floyd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Soultakov Maxim
 */
public class FloydAlgorithm {

    private static final String OUTPUT = "floyd.out";

    private static final String INPUT = "floyd.in";

    private static final int IMPOSSIBLE = 10001;

    private static int[][] graph;

    public static void main(String[] args) throws IOException {
        readData();
        floyd();
        printResult();
    }

    private static void printResult() throws IOException {
        BufferedWriter fw = new BufferedWriter(new FileWriter(OUTPUT));
        for (int i = 1; i < graph.length; i++) {
            for (int j = 1; j < graph.length; j++) {
                fw.write(graph[i][j] + " ");
            }
            fw.newLine();
        }
        fw.close();
    }

    private static void floyd() {
        for (int k = 1; k < graph.length; k++) {
            for (int i = 1; i < graph.length; i++) {
                for (int j = 1; j < graph.length; j++) {
                    if (graph[i][k] != IMPOSSIBLE && graph[i][k] != IMPOSSIBLE) {
                        graph[i][j] = Math.min(graph[i][j], graph[i][k]
                                + graph[k][j]);
                    }
                }
            }
        }
    }

    private static void readData() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(INPUT));
        int size = sc.nextInt() + 1;
        graph = new int[size][size];
        for (int i = 1; i < graph.length; i++) {
            Arrays.fill(graph[i], IMPOSSIBLE);
        }
        sc.nextInt();
        while (sc.hasNext()) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            graph[start][end] = sc.nextInt();
        }
        sc.close();
    }
}

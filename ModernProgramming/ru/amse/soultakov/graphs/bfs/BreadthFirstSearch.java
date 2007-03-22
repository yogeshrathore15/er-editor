/*
 * Created on 18.03.2007
 */
package ru.amse.soultakov.graphs.bfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

    private static final int UNVISITED = Integer.MAX_VALUE;
    private static final String OUTPUT = "breadth.out";
    private static final String INPUT  = "breadth.in";
    
    private static LinkedList<Integer>[] graph;
    private static int[] visited;
   
    private static void readData() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(INPUT));
        String[] split = in.readLine().split("\\s");
        int numVertex = Integer.parseInt(split[0]);
        int numEdges = Integer.parseInt(split[1]);
        graph = new LinkedList[numVertex + 1];
        visited = new int[numVertex + 1];
        for(int i = 0; i < visited.length; i++) {
            visited[i] = UNVISITED;
        }
        for(int i = 0; i < numEdges; i++) {
            String line = in.readLine();
            String[] strings = line.split("\\s");
            int firstVertex = Integer.parseInt(strings[0]);
            int secondVertex = Integer.parseInt(strings[1]);
            addToGraph(firstVertex, secondVertex);
            addToGraph(secondVertex, firstVertex);
        }
        in.close();
    }
    
    private static void bfs(int vertex) {
        visited[vertex] = 0;
        if (visited.length == 2) {
            return;
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(vertex);
        while(queue.size() != 0) {
            int x = queue.poll();
            for(int y : graph[x]) {
                if (visited[y] == UNVISITED) {
                    visited[y] = visited[x] + 1;
                    queue.add(y);
                }
            }
        }
    }
    
    private static void addToGraph(int firstVertex, int secondVertex) {
        if (graph[firstVertex] == null) {
            graph[firstVertex] = new LinkedList<Integer>();
        }
        graph[firstVertex].add(secondVertex);
    }
    
    private static void printResult() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(OUTPUT));
        for (int i = 1; i < visited.length; i++) {
            out.write(visited[i] + " ");
        }
        out.close();
    }

    public static void main(String[] args) throws IOException {
        readData();
        bfs(1);
        printResult();
        for (int i = 1; i < visited.length; i++) {
            System.out.print(visited[i] + " ");
        }
    }

    
}

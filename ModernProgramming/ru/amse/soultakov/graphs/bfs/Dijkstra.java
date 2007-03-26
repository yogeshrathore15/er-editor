/*
 * Created on 26.03.2007
 */
package ru.amse.soultakov.graphs.bfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dijkstra {
    private static final int INFINITY = Integer.MAX_VALUE;

    private static final String OUTPUT = "dijkstra.out";

    private static final String INPUT = "dijkstra.in";

    private final static class Edge implements Comparable<Edge> {
        private int first;

        private int cost;

        private int second;

        public Edge(int first, int second, int cost) {
            this.first = first;
            this.second = second;
            this.cost = cost;
        }

        public final int getFirst() {
            return this.first;
        }

        public final int getSecond() {
            return this.second;
        }

        public final int getCost() {
            return this.cost;
        }

        @Override
        public int hashCode() {
            final int PRIME = 31;
            int result = super.hashCode();
            result = PRIME * result + this.first;
            result = PRIME * result + this.second;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Edge other = (Edge) obj;
            if (this.first != other.first)
                return false;
            if (this.second != other.second)
                return false;
            return true;
        }

        public int compareTo(Edge o) {
            return this.cost - o.cost;
        }
    }

    private static List<Edge>[] graph;

    private static int[] results;

    private static void readData() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(INPUT));
        String[] split = in.readLine().split("\\s");
        int numVertex = Integer.parseInt(split[0]);
        int numEdges = Integer.parseInt(split[1]);
        graph = new LinkedList[numVertex + 1];
        results = new int[numVertex + 1];
        for (int i = 0; i < numEdges; i++) {
            String line = in.readLine();
            String[] strings = line.split("\\s");
            int firstVertex = Integer.parseInt(strings[0]);
            int secondVertex = Integer.parseInt(strings[1]);
            int cost = Integer.parseInt(strings[2]);
            addToGraph(firstVertex, secondVertex, cost);
            addToGraph(secondVertex, firstVertex, cost);
        }
        in.close();
    }

    private static void dijkstra() {
        if (results.length > 2) {
            Arrays.fill(results, INFINITY);
            results[1] = 0;
            for (Edge e : graph[1]) {
                results[e.getSecond()] = e.getCost();
            }
            Set<Integer> set = new HashSet<Integer>(results.length, 1.0f);
            for(int i = 2; i < graph.length; i++) {
            	set.add(i);
            }
            while(!set.isEmpty()) {
            	Integer u = null;
            	int min = INFINITY;
            	for(Integer tmp : set) {
            		if(results[tmp] < min) {
            			min = results[tmp];
            			u = tmp;
            		}
            	}
            	set.remove(u);
            	for(Edge e : graph[u]) {
            		results[e.getSecond()] = Math.min(e.getSecond(), results[u] + e.getCost()); 
            	}
            }
        }
    }

    private static void addToGraph(int firstVertex, int secondVertex, int cost) {
        if (graph[firstVertex] == null) {
            graph[firstVertex] = new LinkedList<Edge>();
        }
        graph[firstVertex].add(new Edge(firstVertex, secondVertex, cost));
    }

    private static void printResult() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(OUTPUT));
        for (int i = 1; i < results.length; i++) {
            out.write(results[i] + " ");
        }
        out.close();
    }

    public static void main(String[] args) throws IOException {
        readData();
        dijkstra();
        printResult();
    }

}

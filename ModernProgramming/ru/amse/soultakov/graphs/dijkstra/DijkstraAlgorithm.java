package ru.amse.soultakov.graphs.dijkstra;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DijkstraAlgorithm {

    private static final String OUTPUT = "dijkstra.out";

    private static final String INPUT = "dijkstra.in";

    private static Graph graph;

    private static DistanceVertex[] va;

    public static void main(String[] args) throws IOException {
        readData();
        dijkstra();
        printResult();
    }

    private static void printResult() throws IOException {
        FileWriter fw = new FileWriter(OUTPUT);
        for (int i = 1; i < va.length; i++) {
            fw.write(va[i].getDistance() + " ");
        }
        fw.close();
    }

    private static void dijkstra() {
        BinaryHeap heap = new BinaryHeap();
        va = new DistanceVertex[graph.size()];
        va[1] = new DistanceVertex(graph.getVertex(1), 0);
        heap.add(va[1]);
        for (int i = 2; i < graph.size(); i++) {
            va[i] = new DistanceVertex(graph.getVertex(i), Integer.MAX_VALUE);
            heap.add(va[i]);
        }
        boolean[] flags = new boolean[graph.size()];
        while (heap.size() > 0) {
            DistanceVertex vertexA = (DistanceVertex) heap.min();
            for (Edge edge : graph.getEdges(vertexA.getVertex().getNumber())) {
                if ((null != edge)
                        && (!flags[edge.getEnd()])
                        && (edge.getSetCost() + vertexA.getDistance() < va[edge
                                .getEnd()].getDistance())) {
                    va[edge.getEnd()].setDistance(vertexA.getDistance()
                            + edge.getSetCost());
                    heap.decreaseKey(va[edge.getEnd()]);
                }
            }
        }
    }

    private static void readData() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUT));
        String line = reader.readLine();
        String[] strings = line.split("\\s");
        int verticesCount = Integer.parseInt(strings[0]) + 1;
        int edgesCount = Integer.parseInt(strings[1]);
        graph = new Graph(verticesCount);
        for (int i = 1; i < verticesCount; i++) {
            graph.addVertex(new Vertex());
        }
        for (int i = 0; i < edgesCount; i++) {
            line = reader.readLine();
            strings = line.split("\\s");
            int start = Integer.parseInt(strings[0]);
            int end = Integer.parseInt(strings[1]);
            int cost = Integer.parseInt(strings[2]);
            graph.addEdge(start, end, cost);
            graph.addEdge(end, start, cost);
        }
        reader.close();
    }
}

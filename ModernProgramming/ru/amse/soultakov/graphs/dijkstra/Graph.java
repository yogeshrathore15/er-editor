package ru.amse.soultakov.graphs.dijkstra;

import java.util.ArrayList;

public class Graph {

    private ArrayList<Edge>[] graph;

    private Vertex[] vertices;

    private int intvertexID = 1;

    @SuppressWarnings("unchecked")
    public Graph(int size) {
        graph = new ArrayList[size];
        vertices = new Vertex[size];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<Edge>();
        }
    }

    public void addEdge(int v1, int v2, int weight) {
        graph[v1].add(new Edge(weight, v2));
    }

    public Edge getEdge(Vertex v1, Vertex v2) {
        for (Edge e : graph[v1.getNumber()]) {
            if (e.getEnd() == v2.getNumber()) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Edge> getEdges(int vertex) {
        return graph[vertex];
    }

    public void addVertex(Vertex v) {
        v.setNumber(intvertexID);
        vertices[intvertexID] = v;
        intvertexID++;
    }

    public int size() {
        return intvertexID;
    }

    public Vertex getVertex(int value) {
        return vertices[value];
    }

}

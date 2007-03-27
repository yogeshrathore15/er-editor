package ru.amse.soultakov.graphs.connectedcomponents;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class ConnectedComponent {
    
	private static final String OUTPUT = "connected.out";
    private static final String INPUT = "connected.in";
    
    private static LinkedList<Integer>[] graph;
    private static int currentComponent;
    private static int[] components;

	public static void main(String[] args) throws IOException {
		readData();
		searchComponents();
        printResult();
	}

    private static void printResult() throws IOException {
        FileWriter fw = new FileWriter(OUTPUT);
		fw.write(currentComponent - 1 + "\r\n");
		for (int i = 1; i < components.length; i++) {
			fw.write(components[i] + " ");
        }
		fw.close();
    }

    private static void searchComponents() {
        LinkedList<Integer> st = new LinkedList<Integer>();
		components = new int[graph.length];
		currentComponent = 1;
		Integer v = 1;
		for (int i = 1; i < components.length; i++) {
			if (components[i] == 0) {
				st.addLast(i);
				while (!st.isEmpty()) {
					v = st.getFirst();
					components[v] = currentComponent;
					st.removeFirst();
					for (Integer j : graph[v]) {
						if (components[j] == 0) {
							st.addLast(j);
						}
					}
				}
				currentComponent++;
			}
		}
    }

    @SuppressWarnings("unchecked")
	private static void readData() throws FileNotFoundException, IOException {
    	BufferedReader reader = new BufferedReader(new FileReader(INPUT));
        String line = reader.readLine();
        String[] strings = line.split("\\s");
        int verticesCount = Integer.parseInt(strings[0]) + 1;
        int edgesCount = Integer.parseInt(strings[1]);
		graph = new LinkedList[verticesCount];
		for (int i = 1; i < verticesCount; i++) {
			graph[i] = new LinkedList<Integer>();
		}
		for (int i = 0; i < edgesCount; i++) {
			line = reader.readLine();
            strings = line.split("\\s");
			int start = Integer.parseInt(strings[0]);
            int end = Integer.parseInt(strings[1]);
			graph[start].addLast(end);
			graph[end].addLast(start);
		}
        reader.close();
    }
    
}

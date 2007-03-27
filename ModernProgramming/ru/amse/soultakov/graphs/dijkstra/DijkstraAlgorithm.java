package ru.amse.soultakov.graphs.dijkstra;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class DijkstraAlgorithm {
	public static void main(String[] args) throws IOException {
		Reader reader = new FileReader("dijkstra.in");
		int n = Character.digit(reader.read(), 10);
		int number = 0;
		while (n != -1) {
			number = number * 10 + n;
			n = Character.digit(reader.read(), 10);
		}
		number++;
		n = Character.digit(reader.read(), 10);
		int count = 0;
		while (n != -1) {
			count = count * 10 + n;
			n = Character.digit(reader.read(), 10);
		}
		n = Character.digit(reader.read(), 10);
		n = Character.digit(reader.read(), 10);
		FileWriter fw = new FileWriter("dijkstra.out");
		Graph graph = new Graph(number);
		for (int i = 0; i < number - 1; i++) {
			graph.addVertex(new Vertex());
		}
		for (int i = 0; i < count; i++) {
			int start = 0;
			while (n != -1) {
				start = start * 10 + n;
				n = Character.digit(reader.read(), 10);
			}
			n = Character.digit(reader.read(), 10);
			int end = 0;
			while (n != -1) {
				end = end * 10 + n;
				n = Character.digit(reader.read(), 10);
			}
			n = Character.digit(reader.read(), 10);
			int weigth = 0;
			while (n != -1) {
				weigth = weigth * 10 + n;
				n = Character.digit(reader.read(), 10);
			}
			n = Character.digit(reader.read(), 10);
			n = Character.digit(reader.read(), 10);
			graph.addEdge(start, end, weigth);
			graph.addEdge(end, start, weigth);
		}

		BinaryHeap heap = new BinaryHeap();
		DistanceVertex[] va = new DistanceVertex[graph.size()];
		for (int i = 1; i < graph.size(); i++) {
			if (i == 1) {
				va[i] = new DistanceVertex(graph.getVertex(i), 0);
				heap.add(va[i]);
			} else {
				va[i] = new DistanceVertex(graph.getVertex(i), Integer.MAX_VALUE);
				heap.add(va[i]);
			}
		}
		boolean[] flags = new boolean[graph.size()];
		while (heap.size() > 0) {
			DistanceVertex vertexA = (DistanceVertex) heap.min();
			for (Edge edge : graph.getEdges(vertexA.getVertex().getNumber())) {
				if ((null != edge)
						&& (!flags[edge.getEnd()])
						&& (edge.getWeight() + vertexA.getDistance() < va[edge
								.getEnd()].getDistance())) {
					va[edge.getEnd()].setDistance(vertexA.getDistance()
							+ edge.getWeight());
					heap.decreaseKey(va[edge.getEnd()]);
				}
			}

		}
		for (int i = 1; i < va.length; i++) {
			fw.write(va[i].getDistance() + " ");
		}
		fw.close();
		reader.close();

	}
}

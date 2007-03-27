package ru.amse.soultakov.graphs.dijkstra;

public class Edge {
	private int weight;

	private int finish;

	public Edge(int weight, int finish) {
		this.weight = weight;
		this.finish = finish;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getEnd() {
		return finish;
	}
}

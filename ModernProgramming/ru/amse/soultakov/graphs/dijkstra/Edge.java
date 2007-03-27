package ru.amse.soultakov.graphs.dijkstra;

public class Edge {
	private int setCost;

	private int finish;

	public Edge(int weight, int finish) {
		this.setCost = weight;
		this.finish = finish;
	}

	public int getSetCost() {
		return setCost;
	}

	public void setSetCost(int weight) {
		this.setCost = weight;
	}

	public int getEnd() {
		return finish;
	}
}

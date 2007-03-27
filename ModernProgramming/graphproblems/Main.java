package graphproblems;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

public class Main {
	private static LinkedList<Integer>[] graph = null;

	public static void main(String[] args) throws IOException {
		Reader reader = new FileReader("breadth.in");
		FileWriter fw = new FileWriter("breadth.out");
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
		graph = new LinkedList[number];
		for (int i = 1; i < number; i++) {
			graph[i] = new LinkedList<Integer>();
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
			n = Character.digit(reader.read(), 10);
			graph[start].addLast(end);
			graph[end].addLast(start);
		}
		LinkedList<Integer> st = new LinkedList<Integer>();
		int[] distance = new int[number];
		Integer v = 1;
		st.addLast(v);
		distance[1] = 1;
		while (st.isEmpty() == false) {
			v = st.getFirst();
			st.removeFirst();
			for (Integer integer : graph[v]) {
				if (distance[integer] == 0) {
					st.addLast(integer);
					distance[integer] = distance[v] + 1;
				}
			}
		}

		for (int i = 1; i < distance.length; i++) {
			fw.write(distance[i] - 1 + " ");
		}
		reader.close();
		fw.close();
	}
}

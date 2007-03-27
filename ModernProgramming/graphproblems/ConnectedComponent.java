package graphproblems;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

public class ConnectedComponent {
    
	private static final String OUTPUT = "connected.out";
    private static final String INPUT = "connected.in";
    
    private static LinkedList<Integer>[] graph;
    private static int currentComponent;
    private static int[] components;

	public static void main(String[] args) throws IOException {
		int number = readData();
		searchComponents(number);
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

    private static void searchComponents(int number) {
        LinkedList<Integer> st = new LinkedList<Integer>();
		components = new int[number];
		currentComponent = 1;
		Integer v = 1;
		for (int i = 1; i < components.length; i++) {
			if (components[i] == 0) {
				st.addLast(i);

				while (st.isEmpty() == false) {
					v = st.getFirst();
					components[v] = currentComponent;
					st.removeFirst();
					for (Integer integer : graph[v]) {
						if (components[integer] == 0) {
							st.addLast(integer);

						}
					}
				}
				currentComponent++;
			}
		}
    }

    private static int readData() throws FileNotFoundException, IOException {
        Reader reader = new FileReader(INPUT);
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
        reader.close();
        return number;
    }
}

package ru.amse.soultakov.structures.lists;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StackTest {
	
	private static final String OUTPUT = "stack.out";
	private static final String INPUT  = "stack.in";
	
	private static final String CMD_PUSH = "push";
	private static final String CMD_POP = "pop";

	private static Stack<String> stack = new Stack<String>(); 
	
	private static void process() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(INPUT));
		BufferedWriter out = new BufferedWriter(new FileWriter(OUTPUT));
		String line;
		String value = null;
		String command;
		while( (line = in.readLine()) != null) {
			String[] strings = line.split("\\s");
			command = strings[0];
			if (strings.length > 1) {
				value = strings[1]; 
			}
			if(command.compareToIgnoreCase(CMD_PUSH) == 0) {
				stack.push(value);
			} else if(command.compareToIgnoreCase(CMD_POP) == 0) {
				try {
					value = stack.pop();
				} catch (IndexOutOfBoundsException e) {
					value = "*";
				}
				out.write(value + '\n');
			}
		}
		out.close();
		in.close();
	}
	
	public static void main(String[] args) {
		try {
			process();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


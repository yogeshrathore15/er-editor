package ru.amse.soultakov.structures.lists;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DeckTest {
	
	private static final String OUTPUT = "deck.out";
	private static final String INPUT  = "deck.in";
	
	private static final String CMD_PUSH_HEAD = "pushhead";
	private static final String CMD_PUSH_TAIL = "pushtail";
	private static final String CMD_POP_HEAD = "pophead";
	private static final String CMD_POP_TAIL = "poptail";

	private static Deck<String> deck = new Deck<String>(); 
	
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
			if(command.compareToIgnoreCase(CMD_PUSH_HEAD) == 0) {
				deck.pushHead(value);
			} else if(command.compareToIgnoreCase(CMD_PUSH_TAIL) == 0) {
				deck.pushTail(value);
			} else if(command.compareToIgnoreCase(CMD_POP_HEAD) == 0) {
				try {
					value = deck.popHead();
				}
				catch (IllegalStateException e) {
					value = "*";
				}
				out.write(value + '\n');
			} else if(command.compareToIgnoreCase(CMD_POP_TAIL) == 0) {
				try {
					value = deck.popTail();
				}
				catch (IllegalStateException e) {
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

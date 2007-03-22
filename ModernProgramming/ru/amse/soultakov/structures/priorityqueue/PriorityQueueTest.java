/*
 * Created on 28.10.2006
 */
package ru.amse.soultakov.structures.priorityqueue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PriorityQueueTest {
    
    private static final String INPUT = "queue.in";
    private static final String OUTPUT = "queue.out";
    
    private static PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
    
    public static final String CMD_PUSH = "push";
    public static final String CMD_EXTRACT = "extract-min";
    public static final String CMD_DECREASE = "decrease-key";
    
    public static void process() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(INPUT));
        BufferedWriter out = new BufferedWriter(new FileWriter(OUTPUT));
        String line;
        Integer value = null;
        String command;
        while( (line = in.readLine()) != null) {
            String[] strings = line.split("\\s");
            command = strings[0];
            value = null;
            if(command.compareToIgnoreCase(CMD_PUSH) == 0) {
                value = new Integer(strings[1]);
                pq.insert(value);
            } else if(command.compareToIgnoreCase(CMD_EXTRACT) == 0) {
                value = pq.extractMin();
                String s = value != null ? value.toString() : "*";
                out.write(s + "\n");
            } else if(command.compareToIgnoreCase(CMD_DECREASE) == 0) {
                int index = Integer.parseInt(strings[1]);
                value = new Integer(strings[2]);
                pq.decreaseKey(index, value);
            }
        }
        out.close();
        in.close();
    }
    
    public static void main(String[] args) throws IOException {
        process();   
    }
}

/*
 * Created on 30.11.2006
 */
package ru.amse.soultakov.structures.disjointset;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ru.amse.soultakov.structures.cartesiantree.CartesianTree;

public class TestDisjointSetDataStructure {

    private static final String INPUT = "dsu.in";
    private static final String OUTPUT = "dsu.out";
    
    private static final String CMD_UNION = "union";
    private static final String CMD_GET = "get";
    
    private static DisjointSetDataStructure dsds;
    
    private static void process() throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(INPUT));
        BufferedWriter out = new BufferedWriter(new FileWriter(OUTPUT));
        String line;
        Integer value = null;
        String command;
        dsds = new DisjointSetDataStructure(Integer.parseInt(in.readLine()));
        while( (line = in.readLine()) != null) {
            String[] strings = line.split("\\s");
            command = strings[0];
            value = Integer.valueOf(strings[1]);
            if(command.compareToIgnoreCase(CMD_UNION) == 0) {
                dsds.union(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
            } else if(command.compareToIgnoreCase(CMD_GET) == 0) {
                out.write(dsds.getMimimum(value) + "\n");
            }
        }
        out.close();
        in.close();
    }
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        process();
    }

}

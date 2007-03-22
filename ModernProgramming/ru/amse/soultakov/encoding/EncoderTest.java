package ru.amse.soultakov.encoding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class EncoderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        if (args.length == 5) {
            try {
                System.setOut(new PrintStream(System.out, true, args[4]));
            } catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported message encoding: " 
                        + e.getMessage());
                System.out.println("Using default one");
            }
		}
		if (args.length < 4) {
			System.out.println("Usage: convert <from-encoding> " +
					"input-file <to-encoding> output-file [message-encoding]");
		} else {
			try {
				Encoder.encode(args[1], args[0], args[3], args[2]);
				System.out.println("Success!");
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (UnsupportedEncodingException e) {
				System.out.println("Unsupported encoding :");
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}

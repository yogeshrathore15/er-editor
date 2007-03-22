package ru.amse.soultakov.encoding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Encoder {
	
	public static void encode(String inFile, String inFileEncoding, String outFile,
			String outFileEncoding)	throws IOException {
		InputStream in = new FileInputStream(inFile);
		InputStreamReader reader = new InputStreamReader(in, inFileEncoding);
		File f = new File(outFile);
		f.createNewFile();
		OutputStream out = new FileOutputStream(f);
		OutputStreamWriter writer = new OutputStreamWriter(out, outFileEncoding);
		int c;
		while((c = reader.read()) != -1) {
			writer.write(c);
		}
		reader.close();
		writer.close();
	}

}

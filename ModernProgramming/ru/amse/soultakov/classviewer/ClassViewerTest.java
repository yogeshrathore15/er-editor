/*
 * Created on 22.10.2006
 */
package ru.amse.soultakov.classviewer;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }

public class ClassViewerTest {

    /**
     * @param args
     */    
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException  {
        String className = args[0];
        String path = "";
        Class classToPrint = null;
        if (args.length > 1) {
            path = args[1];
            URLClassLoader loader = new URLClassLoader(new URL[] { new URL(path)} );
            classToPrint = loader.loadClass(className);
        } else {
            classToPrint = Class.forName(className);
        }
        ClassViewer cv = new ClassViewer(classToPrint);
        cv.printClass();
    }

}

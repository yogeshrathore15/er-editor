/*
 * Created on 06.11.2006
 */
package ru.amse.soultakov.swingtasks.polyclinic;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

public class Main {
    
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
		javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				PolyclinicFrame pf = new PolyclinicFrame();
				pf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pf.pack();
				pf.setVisible(true);
			}
		});
    }
}

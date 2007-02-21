/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * @author sma
 *
 */
public class Main
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Stupid test");
		frame.add(new DiagramEditor());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

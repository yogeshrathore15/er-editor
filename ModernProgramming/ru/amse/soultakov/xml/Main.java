/**
 * 
 */
package ru.amse.soultakov.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * �����, ���������� ������� �������.
 * 
 * @author Soultakov Maxim
 * 
 */
public class Main
{
	/**
	 * ������� ������� ���������. ���������� ����� <code>XmlProcesser</code> ��� ��������� xml ���������
	 * � ������������ �� ������, ���� ��� �������, � DTD � ���� ������ � ������ ����� �������� ��������� "price"
	 * 
	 * @param args ������ ������� ������� - ���� xml, ������(�����������) - ���� � xml ������
	 */
	public static void main(String[] args)
	{
		XmlProcesser xmlProcesser = null;
		try
		{
			if (args.length == 1)
			{
				xmlProcesser = new XmlProcesser(new File(args[0]));
				System.out.println(xmlProcesser.getPricesSum());
			}
			else if (args.length == 2)
			{
				xmlProcesser = new XmlProcesser(new File(args[0]), new File(args[1]));
				System.out.println(xmlProcesser.getPricesSum());
			}
			else
			{
				System.err.println("Wrong parameters count!");
				System.err
						.println("Usage : java XmlValidator xml_file_name [xml_schema_file_name]");
				return;
			}
		}
		catch (ParserConfigurationException e)
		{
			System.err.println("Bad parser configuration!");
			System.err.println(e.getLocalizedMessage());
		}
		catch (SAXException e)
		{
			System.err.println("SAX error");
			System.err.println(e.getLocalizedMessage());
		}
		catch (IOException e)
		{
			System.err.println("Some IO problem");
			System.err.println(e.getLocalizedMessage());
		}
	}
}

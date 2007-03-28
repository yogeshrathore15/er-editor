/**
 * 
 */
package ru.amse.soultakov.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * ����� ��� ������ � xml ������� � �� ��������� � ����������� �� ������, �
 * ����� �������� ����� �������� ���� ��������� � ������ "price"
 * 
 * @author Soultakov Maxim
 * 
 */
public class XmlProcesser
{

	/**
     * ���������� ������ SAX �������.
     * 
     * @author Soultakov Maxim
     * 
     */
	private final class MyErrorHandler implements ErrorHandler
	{

		/**
         * {@inheritDoc}
         */
		public void error(SAXParseException exception) throws SAXException
		{
			System.err.println("ERROR at line " + exception.getLineNumber() + ": "
					+ exception.getLocalizedMessage());
		}

		/**
         * {@inheritDoc}
         */
		public void fatalError(SAXParseException exception) throws SAXException
		{
			System.err.println("FATAL ERROR at line " + exception.getLineNumber() + ": "
					+ exception.getLocalizedMessage());
		}

		/**
         * {@inheritDoc}
         */
		public void warning(SAXParseException exception) throws SAXException
		{
			System.err.println("WARNING at line " + exception.getLineNumber() + ": "
					+ exception.getLocalizedMessage());
		}
	}

	/**
     * ��� ���������, ��� �������� �������� ���������� ���������� �����
     */
	private static final String PRICE = "price";

	/**
     * ��������, ��������������� ������������ xml-�����
     */
	private Document xmlDocument;

	/**
     * ����� ���������, ������� �������� "price"
     */
	private double sum;

	/**
     * ��������� �������� <code>true</code>, ���� ����� ��� ����������
     */
	private boolean sumIsCalculated = false;

	/**
     * ������� ��������� ������. Xml �������� ����������� � �������� ��������� �
     * ������������ � ������������ � DTD �������
     * 
     * @param xmlFile
     *            xml ����
     * @throws ParserConfigurationException
     *             ���� ���������� ������ ������������ �������
     * @throws SAXException
     *             ���� �������� ������ ��� ������� ���������
     * @throws IOException
     *             ���� ���� ��������� ��� ����� �� ����������
     */
	public XmlProcesser(File xmlFile) throws ParserConfigurationException, SAXException,
			IOException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		dbf.setNamespaceAware(true);
		DocumentBuilder xmlDocumentBuilder = dbf.newDocumentBuilder();
		xmlDocumentBuilder.setErrorHandler(new MyErrorHandler());
		xmlDocument = xmlDocumentBuilder.parse(xmlFile);
	}

	/**
     * ������� ��������� ������. Xml �������� ����������� � �������� ��������� �
     * ������������ � ������������ �� ������ xml, ����� ��������� � ��������
     * ���������
     * 
     * @param xmlFile
     *            xml ����
     * @param schema
     *            ����� ��� ��������� xml
     * @throws ParserConfigurationException
     *             ���� ���������� ������ ������������ �������
     * @throws SAXException
     *             ���� �������� ������ ��� ������� ���������
     * @throws IOException
     *             ���� ���� ��������� ��� ����� �� ����������
     */
	public XmlProcesser(File xmlFile, File schemaFile) throws ParserConfigurationException,
			SAXException, IOException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(schemaFile);
		dbf.setNamespaceAware(true);
		dbf.setValidating(false);
		// ���� �� ���������� �����, �� ���������
		// "�������" ��������� (� ����������� ������ o_O)
		dbf.setSchema(schema);
		DocumentBuilder xmlDocumentBuilder = dbf.newDocumentBuilder();
		xmlDocumentBuilder.setErrorHandler(new MyErrorHandler());
		xmlDocument = xmlDocumentBuilder.parse(xmlFile);
	}

	/**
     * ����� �������� ���� ��������� � ������ "price", ������������ � xml
     * ���������
     * 
     * @return ����� �������� ���� ��������� � ������ "price", ������������ �
     *         xml ���������
     */
	public double getPricesSum()
	{
		if (!sumIsCalculated)
		{
			calculatePricesSum(xmlDocument);
			sumIsCalculated = true;
		}
		return sum;
	}

	/**
     * ��������������� ������� ��� ������� ����� �������� ���� ��������� �
     * ������ "price", ������������ � xml ���������. ���������� �������� ���� ����
     * 
     * @param node ������� ���� ��� �������
     */
	private void calculatePricesSum(Node node)
	{
		int type = node.getNodeType();
		switch (type)
		{
			case Node.DOCUMENT_NODE:
			{
				calculatePricesSum(((Document) node).getDocumentElement());
				break;
			}
			case Node.ATTRIBUTE_NODE:
			{
				if (node.getNodeName().equals(PRICE))
				{
					sum += Double.parseDouble(node.getNodeValue());
				}
				break;
			}
			case Node.ELEMENT_NODE:
			{
				NamedNodeMap attrs = node.getAttributes();
				for (int i = 0; i < attrs.getLength(); i++)
				{
					calculatePricesSum(attrs.item(i));
				}
				if (node.hasChildNodes())
				{
					NodeList children = node.getChildNodes();
					for (int i = 0; i < children.getLength(); i++)
					{
						calculatePricesSum(children.item(i));
					}
				}
				break;
			}
		}
	}

}

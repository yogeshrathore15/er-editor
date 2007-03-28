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
 * Класс для работы с xml файлами и их валидации в соответсвие со схемой, а
 * также подсчета суммы значений всех атрибутов с именем "price"
 * 
 * @author Soultakov Maxim
 * 
 */
public class XmlProcesser
{

	/**
     * Обработчик ошибок SAX парсера.
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
     * Имя аттрибута, для значений которого необходимо подсчитать сумму
     */
	private static final String PRICE = "price";

	/**
     * Документ, соответствующий разобранному xml-файлу
     */
	private Document xmlDocument;

	/**
     * Сумма атрибутов, имеющих значение "price"
     */
	private double sum;

	/**
     * Принимает значение <code>true</code>, если сумма уже подсчитана
     */
	private boolean sumIsCalculated = false;

	/**
     * Создает экземпляр класса. Xml документ указывается в качестве параметра и
     * валидируется в соответствии с DTD схемаой
     * 
     * @param xmlFile
     *            xml файл
     * @throws ParserConfigurationException
     *             если существует ошибка конфигурации парсера
     * @throws SAXException
     *             если возникла ошибка при разборе документа
     * @throws IOException
     *             если файл документа или схемы не существует
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
     * Создает экземпляр класса. Xml документ указывается в качестве параметра и
     * валидируется в соответствии со схемой xml, также указанной в качестве
     * параметра
     * 
     * @param xmlFile
     *            xml файл
     * @param schema
     *            схема для валидации xml
     * @throws ParserConfigurationException
     *             если существует ошибка конфигурации парсера
     * @throws SAXException
     *             если возникла ошибка при разборе документа
     * @throws IOException
     *             если файл документа или схемы не существует
     */
	public XmlProcesser(File xmlFile, File schemaFile) throws ParserConfigurationException,
			SAXException, IOException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(schemaFile);
		dbf.setNamespaceAware(true);
		dbf.setValidating(false);
		// если мы используем схему, то отключаем
		// "обычную" валидацию (в презентации ошибка o_O)
		dbf.setSchema(schema);
		DocumentBuilder xmlDocumentBuilder = dbf.newDocumentBuilder();
		xmlDocumentBuilder.setErrorHandler(new MyErrorHandler());
		xmlDocument = xmlDocumentBuilder.parse(xmlFile);
	}

	/**
     * Сумму значений всех атрибутов с именем "price", содержащихся в xml
     * документе
     * 
     * @return сумму значений всех атрибутов с именем "price", содержащихся в
     *         xml документе
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
     * Вспомогательная функция для посчета суммы значений всех атрибутов с
     * именем "price", содержащихся в xml документе. Рекурсивно вызывает сама себя
     * 
     * @param node текущий узел для разбора
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

package ru.soultakov.remotecontrol.ant;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static junit.framework.Assert.assertEquals;

import static org.junit.Assert.fail;

public class AntTaskInvokerTest {

    public static final String EXPRECTED_XML = "<project>\r\n<target name=\"target\">\r\n<echo message=\"Hello World\"/>\r\n</target>\r\n</project>\r\n";
    public static final String ECHO_TASK = "<echo message=\"Hello World\" />";
    public static final String EXPECTED_RESULT = "\r\ntarget:\r\n     [echo] Hello World\r\n\r\nBUILD SUCCESSFUL\r\nTotal time: 0 seconds\r\n";

    @Test(expected = IllegalArgumentException.class)
    public void testInvokeTask1() throws AntTaskInvokationException {
        AntTaskInvoker.invokeTask(null);
    }

    @Test
    public void testInvokeTask2() throws AntTaskInvokationException {
        final String result = AntTaskInvoker.invokeTask(ECHO_TASK);
        assertEquals(result, EXPECTED_RESULT);
    }

    @Test
    public void testGetAntBuildFileDocument() throws ParserConfigurationException, SAXException,
            IOException {
        final Document doc = AntTaskInvoker.getAntBuildFileDocument(ECHO_TASK);
        final Element project = doc.getDocumentElement();
        assertEquals(project.getNodeName(), AntTaskInvoker.PROJECT);
        final Element target = findChildByName(project, AntTaskInvoker.TARGET);
        final Element echo = findChildByName(target, "echo");
        assertEquals(echo.getAttribute("message"), "Hello World");
    }

    private static Element findChildByName(final Element element, String name) {
        final NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node item = childNodes.item(i);
            if (name.equals(item.getNodeName())) {
                return (Element) item;
            } else if ((i == childNodes.getLength() - 1)) {
                fail("No child with name " + name);
            }
        }
        return null;
    }

    @Test
    public void testSaveXmlToFile() throws ParserConfigurationException, TransformerException,
            IOException, SAXException {
        final Document doc = AntTaskInvoker.getAntBuildFileDocument(ECHO_TASK);
        final StringWriter writer = new StringWriter();
        AntTaskInvoker.saveXmlToWriter(doc, writer);
        assertEquals(writer.getBuffer().toString(), EXPRECTED_XML);
        assertEquals(doc, doc);
    }
}

package ru.soultakov.remotecontrol.ant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class AntTaskInvoker {

    static final String TARGET_NAME = "target";
    static final String NAME = "name";
    static final String TARGET = "target";
    static final String PROJECT = "project";

    private static final Object LOCK = new Object();

    public static String invokeTask(String task) throws AntTaskInvokationException {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }
        synchronized (LOCK) {
            try {
                final Document antBuildFileDocument = getAntBuildFileDocument(task);
                final File antBuildFile = saveXmlToTempFile(antBuildFileDocument);
                return executeAntBuild(antBuildFile);
            } catch (final ParserConfigurationException e) {
                throw new AntTaskInvokationException(e);
            } catch (final TransformerException e) {
                throw new AntTaskInvokationException(e);
            } catch (final IOException e) {
                throw new AntTaskInvokationException(e);
            } catch (final SAXException e) {
                throw new AntTaskInvokationException(e);
            }
        }
    }

    private static String executeAntBuild(File antBuildFile) {
        final ProjectHelper projectHelper = ProjectHelper.getProjectHelper();
        final Project project = new Project();
        project.init();
        projectHelper.parse(project, antBuildFile);
        final ByteArrayOutputStream output = setUpOutput(project);
        project.executeTarget(TARGET_NAME);
        project.fireBuildFinished(null);
        return output.toString();
    }

    private static ByteArrayOutputStream setUpOutput(final Project project) {
        final DefaultLogger consoleLogger = new DefaultLogger();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(output);
        consoleLogger.setErrorPrintStream(printStream);
        consoleLogger.setOutputPrintStream(printStream);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(consoleLogger);
        return output;
    }

    static File saveXmlToTempFile(Document document) throws TransformerException, IOException {
        final File file = File.createTempFile(NAME, TARGET_NAME);
        file.deleteOnExit();
        final Writer fileWriter = new FileWriter(file);
        saveXmlToWriter(document, fileWriter);
        return file;
    }

    static void saveXmlToWriter(Document document, final Writer writer)
            throws TransformerFactoryConfigurationError, TransformerConfigurationException,
            TransformerException, IOException {
        final Transformer transformer = createTransformer();
        try {
            final StreamResult result = new StreamResult(writer);
            final DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } finally {
            writer.close();
        }
    }

    private static Transformer createTransformer() throws TransformerFactoryConfigurationError,
            TransformerConfigurationException {
        final TransformerFactory transfac = TransformerFactory.newInstance();
        final Transformer transformer = transfac.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        return transformer;
    }

    static Document getAntBuildFileDocument(String task) throws ParserConfigurationException,
            SAXException, IOException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final Document antBuildFileDocument = documentBuilder.newDocument();

        final Element projectElement = antBuildFileDocument.createElement(PROJECT);
        antBuildFileDocument.appendChild(projectElement);
        final Element targetElement = antBuildFileDocument.createElement(TARGET);
        targetElement.setAttribute(NAME, TARGET_NAME);
        projectElement.appendChild(targetElement);
        final Document taskDocument = documentBuilder.parse(new ByteArrayInputStream(task
                .getBytes()));
        final Element taskElement = taskDocument.getDocumentElement();
        final Node antDocumentTaskElement = antBuildFileDocument.importNode(taskElement, true);
        targetElement.appendChild(antDocumentTaskElement);
        return antBuildFileDocument;
    }

}

package ru.soultakov.remotecontrol.ant;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class AntTaskInvoker {

    private static final Logger LOGGER = Logger.getLogger(AntTaskInvoker.class);

    public static class LogErrorHandler implements ErrorHandler {

        public static final ErrorHandler INSTANCE = new LogErrorHandler();

        @Override
        public void error(SAXParseException exception) throws SAXException {
            LOGGER.info(exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            LOGGER.info(exception.getMessage());
        }

        @Override
        public void warning(SAXParseException exception) throws SAXException {
        }

    }

    static final String TARGET_NAME = "target";
    static final String NAME = "name";
    static final String TARGET = "target";
    static final String PROJECT = "project";

    private static final Object LOCK = new Object();

    public static String invokeTask(String task) throws AntTaskInvokationException {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }
        LOGGER.info("Invoking task '" + task + "'");
        synchronized (LOCK) {
            try {
                final Document antBuildFileDocument = getAntBuildFileDocument(task);
                LOGGER.info("Ant build file document created");
                final File antBuildFile = saveXmlToTempFile(antBuildFileDocument);
                LOGGER.info("Ant build file created and saved to disc");
                final String result = executeAntBuild(antBuildFile);
                LOGGER.info("Task successfully invoked. Result = '" + result + "'");
                return result;
            } catch (final ParserConfigurationException e) {
                LOGGER.error(e.getMessage());
                throw new AntTaskInvokationException(e);
            } catch (final TransformerException e) {
                LOGGER.error(e.getMessage());
                throw new AntTaskInvokationException(e);
            } catch (final IOException e) {
                LOGGER.error(e.getMessage());
                throw new AntTaskInvokationException(e);
            } catch (final SAXException e) {
                LOGGER.info(e.getMessage());
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
        documentBuilder.setErrorHandler(LogErrorHandler.INSTANCE);
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

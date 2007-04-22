package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_DIAGRAM;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_MODEL;

import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.view.Diagram;
import ru.amse.soultakov.ereditor.view.DiagramLoadingException;
import ru.amse.soultakov.ereditor.view.IDiagramLoader;

public class XmlDiagramLoader implements IDiagramLoader {

    private final InputStream inputStream;

    private final LoadingIdManager loadingIdManager = new LoadingIdManager();;

    public XmlDiagramLoader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Diagram loadDiagram() throws DiagramLoadingException {
        Document doc = getDocument(new SAXBuilder());
        DiagramLoader dl = new DiagramLoader(loadingIdManager, doc.getRootElement()
                .getChild(TAG_DIAGRAM));
        return dl.load();
    }

    private Document getDocument(SAXBuilder builder) throws DiagramLoadingException {
        Document doc = null;
        try {
            doc = builder.build(inputStream);
        } catch (JDOMException e) {
            throw new DiagramLoadingException(e);
        } catch (IOException e) {
            throw new DiagramLoadingException(e);
        }
        return doc;
    }

    public ERModel loadModel() throws DiagramLoadingException {
        ERModelLoader erml = new ERModelLoader(loadingIdManager, getDocument(
                new SAXBuilder()).getRootElement().getChild(TAG_MODEL));
        ERModel model = erml.load();
        return model;
    }
}

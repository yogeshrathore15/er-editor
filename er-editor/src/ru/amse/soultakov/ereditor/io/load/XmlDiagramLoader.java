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

public class XmlDiagramLoader {

    private final InputStream inputStream;

    public XmlDiagramLoader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Diagram load() throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(inputStream);
        LoadingIdManager loadingIdManager = new LoadingIdManager();
        ERModelLoader erml = new ERModelLoader(loadingIdManager, doc
                .getRootElement().getChild(TAG_MODEL));
        ERModel model = erml.load();
        DiagramLoader dl = new DiagramLoader(loadingIdManager, doc.getRootElement()
                .getChild(TAG_DIAGRAM), model);
        return dl.load();
    }
}

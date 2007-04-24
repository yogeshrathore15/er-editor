package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_DIAGRAM;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_MODEL;

import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import ru.amse.soultakov.ereditor.controller.IProgressMonitor;
import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.view.Diagram;
import ru.amse.soultakov.ereditor.view.DiagramLoadingException;
import ru.amse.soultakov.ereditor.view.IDiagramLoader;

public class XmlDiagramLoader implements IDiagramLoader {

    private final LoadingIdManager loadingIdManager = new LoadingIdManager();
    
    private final Document document;

    public XmlDiagramLoader(InputStream inputStream) throws DiagramLoadingException {
        try {
            document = new SAXBuilder().build(inputStream);
        } catch (JDOMException e) {
            throw new DiagramLoadingException(e);
        } catch (IOException e) {
            throw new DiagramLoadingException(e);
        }
    }

    public Diagram loadDiagram(IProgressMonitor monitor) throws DiagramLoadingException {
        DiagramLoader dl = new DiagramLoader(loadingIdManager, document.getRootElement()
                .getChild(TAG_DIAGRAM));
        return dl.load(monitor);
    }

    public ERModel loadModel(IProgressMonitor monitor) throws DiagramLoadingException {
        ERModelLoader erml = new ERModelLoader(loadingIdManager, document.getRootElement().getChild(TAG_MODEL));
        ERModel model = erml.load(monitor);
        return model;
    }
}

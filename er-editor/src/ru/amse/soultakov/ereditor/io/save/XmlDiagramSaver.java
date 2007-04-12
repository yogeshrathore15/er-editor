package ru.amse.soultakov.ereditor.io.save;

import java.io.IOException;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.io.XmlTagConstants;
import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.view.Diagram;
import ru.amse.soultakov.ereditor.view.DiagramSavingException;
import ru.amse.soultakov.ereditor.view.IDiagramSaver;

public class XmlDiagramSaver implements IDiagramSaver {

	private OutputStream outputStream;
    
    public XmlDiagramSaver(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    
    public void save(Diagram diagram, ERModel erModel) throws DiagramSavingException {
        IdManager idManager = new IdManager();
        Element root = new Element(XmlTagConstants.TAG_ER_DIAGRAM);
        Document doc = new Document(root);
        root.addContent(new ERModelSaver(erModel, idManager).save());
        root.addContent(new DiagramViewSaver(diagram, idManager).save());
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        try {
            out.output(doc, outputStream);
        } catch (IOException e) {
            throw new DiagramSavingException(e);
        }
    }

}

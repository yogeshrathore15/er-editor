package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_COMMENTS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_ENTITIES;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.io.save.ERModelSaver;
import ru.amse.soultakov.ereditor.model.ERModel;

public class ERModelLoader {

    private final IdManager idManager;

    private final Element erModelElement;

    private ERModel erModel;

    public ERModelLoader(IdManager idManager, Element erModelElement) {
        this.idManager = idManager;
        this.erModelElement = erModelElement;
    }

    public ERModel load() {
        erModel = new ERModel();
        new EntitiesLoader(idManager, erModel, erModelElement.getChild(TAG_ENTITIES))
                .loadFirst();
        new CommentsLoader(idManager, erModel, erModelElement.getChild(TAG_COMMENTS))
                .loadFirst();
        return erModel;
    }

    public static void main(String[] args) throws IOException, JDOMException {
        ERModelSaver.main(args);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File("model.xml"));
        ERModelLoader erml = new ERModelLoader(new IdManager(), doc.getRootElement());
        ERModel model = erml.load();

        ERModelSaver erSaver = new ERModelSaver(model, new IdManager());
        Document doc2 = new Document(erSaver.save());
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        out.output(doc2, System.out);
    }

}

package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_COMMENTS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_ENTITIES;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_LINKS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_RELATIONSHIPS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.amse.soultakov.ereditor.controller.IProgressMonitor;
import ru.amse.soultakov.ereditor.io.save.ERModelSaver;
import ru.amse.soultakov.ereditor.io.save.SavingIdManager;
import ru.amse.soultakov.ereditor.model.ERModel;

public class ERModelLoader {

    private final LoadingIdManager loadingIdManager;

    private final Element erModelElement;

    public ERModelLoader(LoadingIdManager loadingIdManager, Element erModelElement) {
        this.loadingIdManager = loadingIdManager;
        this.erModelElement = erModelElement;
    }

    public ERModel load(IProgressMonitor monitor) {
        ERModel erModel = new ERModel();
        monitor.setProgress(0);
        new EntitiesLoader(loadingIdManager, erModel, erModelElement
                .getChild(TAG_ENTITIES)).load();
        monitor.setProgress(20);
        new CommentsLoader(loadingIdManager, erModel, erModelElement
                .getChild(TAG_COMMENTS)).load();
        monitor.setProgress(30);
        new RelationshipsLoader(loadingIdManager, erModel, erModelElement
                .getChild(TAG_RELATIONSHIPS)).load();
        monitor.setProgress(45);
        new LinksLoader(loadingIdManager, erModel, erModelElement
                .getChild(TAG_LINKS)).load();
        monitor.setProgress(50);
        return erModel;
    }

    public static void main(String[] args) throws IOException, JDOMException {
        ERModelSaver.main(args);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File("model.xml"));
        ERModelLoader erml = new ERModelLoader(new LoadingIdManager(), doc
                .getRootElement());
        ERModel model = erml.load(new IProgressMonitor() {

            public void done() {
            }

            public void setNote(String note) {
            }

            public void setProgress(int progress) {
            }
            
        });

        ERModelSaver erSaver = new ERModelSaver(model, new SavingIdManager());
        Document doc2 = new Document(erSaver.save());
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        out.output(doc2, new FileOutputStream("model1.xml"));
    }

}

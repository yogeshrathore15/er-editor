package ru.amse.soultakov.ereditor.io.save;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_MODEL;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.amse.soultakov.ereditor.model.Attribute;
import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.FKAttribute;
import ru.amse.soultakov.ereditor.model.SimpleAttributeType;

/**
 * @author Soultakov Maxim
 * 
 */
public class ERModelSaver {

    private final ERModel erModel;

    private final SavingIdManager savingIdManager;

    public ERModelSaver(ERModel erModel, SavingIdManager savingIdManager) {
        this.erModel = erModel;
        this.savingIdManager = savingIdManager;
    }

    public Element save() {
        Element element = new Element(TAG_MODEL);
        element.addContent(new EntitiesSaver(savingIdManager, erModel.getEntities())
                .save());
        element.addContent(new CommentsSaver(savingIdManager, erModel.getComments())
                .save());
        element.addContent(new RelationshipsSaver(savingIdManager, erModel
                .getRelationships()).save());
        element.addContent(new LinksSaver(savingIdManager, erModel.getLinks())
                .save());
        return element;
    }

    public ERModel getERModel() {
        return this.erModel;
    }

    /**
     * @return the idManager
     */
    public SavingIdManager getIdManager() {
        return this.savingIdManager;
    }

    public static void main(String[] args) throws IOException {
        ERModel er = new ERModel();
        
        Entity entity1 = er.addNewEntity();
        entity1.addAttribute(new Attribute("MyName", SimpleAttributeType.INTEGER,
                false, "10"));
        
        Entity entity2 = er.addNewEntity();
        entity1.addAttribute(new FKAttribute("foreign", false, "Empty", entity2,
                entity2.getAttributes().iterator().next()));
        
        er.addNewRealtionship(entity1, entity2);
        Comment comment = er.addNewComment();
        er.addNewLink(entity1, comment);

        ERModelSaver erSaver = new ERModelSaver(er, new SavingIdManager());
        Document doc = new Document(erSaver.save());
        
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        FileOutputStream fos = new FileOutputStream("model.xml");
        out.output(doc, fos);
        fos.close();
    }

}

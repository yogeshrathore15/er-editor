package ru.amse.soultakov.ereditor.io;

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

    private final IdManager idManager;

    public ERModelSaver(ERModel erModel, IdManager idManager) {
        this.erModel = erModel;
        this.idManager = idManager;
    }

    public Element save() {
        Element element = new Element("model");
        element.addContent(new EntitiesSaver(idManager, erModel.getEntities())
                .save());
        element.addContent(new CommentsSaver(idManager, erModel.getComments())
                .save());
        element.addContent(new RelationshipsSaver(idManager, erModel
                .getRelationships()).save());
        element.addContent(new LinksSaver(idManager, erModel.getLinks()).save());
        return element;
    }

    public ERModel getERModel() {
        return this.erModel;
    }

    /**
     * @return the idManager
     */
    public IdManager getIdManager() {
        return this.idManager;
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

        ERModelSaver erSaver = new ERModelSaver(er, new IdManager());
        Document doc = new Document(erSaver.save());
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        out.output(doc, System.out);
    }

}

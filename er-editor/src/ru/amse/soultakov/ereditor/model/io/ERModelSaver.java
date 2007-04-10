package ru.amse.soultakov.ereditor.model.io;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.amse.soultakov.ereditor.model.Attribute;
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
        Entity e = er.addNewEntity();
        e.addAttribute(new Attribute("MyName", SimpleAttributeType.INTEGER, false,
                "10"));
        Entity e1 = er.addNewEntity();
        e.addAttribute(new FKAttribute("foreign", false, "Empty", e1, e1
                .getAttributes().iterator().next()));
        ERModelSaver erSaver = new ERModelSaver(er, new IdManager());
        Document doc = new Document(erSaver.save());
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        out.output(doc, System.out);
    }

}

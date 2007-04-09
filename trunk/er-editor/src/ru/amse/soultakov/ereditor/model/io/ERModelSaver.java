package ru.amse.soultakov.ereditor.model.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;
import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collection;
import java.util.HashMap;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.ERModel;

/**
 * @author Soultakov Maxim
 *
 */
public class ERModelSaver {

    private final ERModel erModel;
    
    private final HashMap<Object, Integer> identifiers;

    public ERModelSaver(ERModel erModel, HashMap<Object, Integer> identifiers) {
        this.erModel = erModel;
        this.identifiers = identifiers;
    }
    
    public Element save() {
        Element element = new Element("model");
        element.addContent(getEntities());
        return element;
    }

    private Collection<Element> getEntities() {
        Collection<Element> elements = newLinkedHashSet();
        
        return elements;
    }

    public ERModel getERModel() {
        return this.erModel;
    }

}

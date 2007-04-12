package ru.amse.soultakov.ereditor.io.load;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.model.ERModel;

public class ERModelLoader {

    private final IdManager idManager;
    
    private final Element erModelElement;

    public ERModelLoader(IdManager idManager, Element erModelElement) {
        this.idManager = idManager;
        this.erModelElement = erModelElement;
    }
    
    public ERModel load() {
        return null;
    }
    
}

package ru.amse.soultakov.ereditor.io.save;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.view.Diagram;

/**
 * @author Soultakov Maxim
 *
 */
public class DiagramSaver {

    private final IdManager idManager;
    
    private final Diagram diagram;

    public DiagramSaver(final IdManager idManager, final Diagram diagram) {
        this.idManager = idManager;
        this.diagram = diagram;
    }
    
    public Element save() {
        Element root = new Element("diagram");
        
        return root;
    }
}

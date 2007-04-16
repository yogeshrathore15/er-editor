package ru.amse.soultakov.ereditor.io.load;

import org.jdom.Element;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.*;

import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.view.Diagram;
import ru.amse.soultakov.ereditor.view.EntityView;

/**
 * @author Soultakov Maxim
 * 
 */
public class DiagramLoader {

    private final LoadingIdManager loadingIdManager;

    private final Element diagramElement;

    private final ERModel erModel;

    public DiagramLoader(LoadingIdManager loadingIdManager, Element erModelElement,
            ERModel erModel) {
        this.loadingIdManager = loadingIdManager;
        this.diagramElement = erModelElement;
        this.erModel = erModel;
    }

    public Diagram load() {
        Diagram diagram = new Diagram(erModel);
        processEntities(diagram, diagramElement.getChild(TAG_ENTITY_VIEWS));
        processComments(diagram);
        processLinks(diagram);
        processRelationships(diagram);
        return diagram;
    }

    private void processEntities(Diagram diagram, Element element) {
        for (Object object : element.getChildren()) {
            if (object instanceof Element) {
                Element entityElement = (Element) object;
                Entity entity = (Entity) loadingIdManager.getObject(entityElement
                        .getAttributeValue(ATTR_ID));
                int x = Integer.parseInt(entityElement.getAttributeValue(ATTR_X));
                int y = Integer.parseInt(entityElement.getAttributeValue(ATTR_Y));
                EntityView entityView = new EntityView(diagram, entity, x, y);
                loadingIdManager.putObject(entityElement.getAttributeValue(ATTR_ID), entityView);
                diagram.addEntityView(entityView);
            }
        }
    }

    private void processComments(Diagram diagram) {
    }

    private void processLinks(Diagram diagram) {

    }

    private void processRelationships(Diagram diagram) {

    }

}

package ru.amse.soultakov.ereditor.io.save;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_COMMENT;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_DIAGRAM;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ENTITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ID;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_LINK;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_RELATIONSHIP;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_X;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_Y;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_COMMENT_VIEW;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_COMMENT_VIEWS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_DIAGRAM;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_ENTITY_VIEW;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_ENTITY_VIEWS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_LINK_VIEWS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_RELATIONSHIP_VIEW;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_RELATIONSHIP_VIEWS;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.Diagram;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.LinkView;
import ru.amse.soultakov.ereditor.view.RelationshipView;

/**
 * @author Soultakov Maxim
 *
 */
class DiagramViewSaver {

	private final SavingIdManager savingIdManager;
    
    private final Diagram diagram;

    public DiagramViewSaver(final Diagram diagram, final SavingIdManager savingIdManager) {
        this.savingIdManager = savingIdManager;
        this.diagram = diagram;
    }
    
    public Element save() {
        Element root = new Element(TAG_DIAGRAM);
        root.addContent(getEntityViewsElement());
        root.addContent(getCommentViewsElement());
        root.addContent(getLinkViewsElement());
        root.addContent(getRelationshipViewsElement());
        return root;
    }

    private Content getCommentViewsElement() {
        Element root = new Element(TAG_COMMENT_VIEWS);
        for(CommentView cv : diagram.getCommentViews()) {
            Element element = new Element(TAG_COMMENT_VIEW);
            element.setAttribute(ATTR_ID, savingIdManager.getId(cv));
            element.setAttribute(ATTR_DIAGRAM, savingIdManager.getId(cv.getDiagram()));
            element.setAttribute(ATTR_COMMENT, savingIdManager.getId(cv.getComment()));
            element.setAttribute(ATTR_X,String.valueOf(cv.getX()));
            element.setAttribute(ATTR_Y,String.valueOf(cv.getY()));
            root.addContent(element);
        }
        return root;
    }

    private Content getLinkViewsElement() {
        Element root = new Element(TAG_LINK_VIEWS);
        for(LinkView lv : diagram.getLinkViews()) {
            Element element = new Element("link_view");
            element.setAttribute(ATTR_ID, savingIdManager.getId(lv));
            element.setAttribute(ATTR_DIAGRAM, savingIdManager.getId(lv.getDiagram()));
            element.setAttribute(ATTR_LINK, savingIdManager.getId(lv.getLink()));
            root.addContent(element);
        }
        return root;
    }

    private Content getRelationshipViewsElement() {
        Element root = new Element(TAG_RELATIONSHIP_VIEWS);
        for(RelationshipView rv : diagram.getRelationshipViews()) {
            Element element = new Element(TAG_RELATIONSHIP_VIEW);
            element.setAttribute(ATTR_ID, savingIdManager.getId(rv));
            element.setAttribute(ATTR_DIAGRAM, savingIdManager.getId(rv.getDiagram()));
            element.setAttribute(ATTR_RELATIONSHIP, savingIdManager.getId(rv.getRelationship()));
            root.addContent(element);
        }
        return root;
    }

    private Content getEntityViewsElement() {
        Element root = new Element(TAG_ENTITY_VIEWS);
        for(EntityView ev : diagram.getEntityViews()) {
            Element element = new Element(TAG_ENTITY_VIEW);
            element.setAttribute("id", savingIdManager.getId(ev));
            element.setAttribute(ATTR_DIAGRAM, savingIdManager.getId(ev.getDiagram()));
            element.setAttribute(ATTR_ENTITY, savingIdManager.getId(ev.getEntity()));
            element.setAttribute(ATTR_X,String.valueOf(ev.getX()));
            element.setAttribute(ATTR_Y,String.valueOf(ev.getY()));
            root.addContent(element);
        }
        return root;
    }
}

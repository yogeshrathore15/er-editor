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

import ru.amse.soultakov.ereditor.io.IdManager;
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

	private final IdManager idManager;
    
    private final Diagram diagram;

    public DiagramViewSaver(final Diagram diagram, final IdManager idManager) {
        this.idManager = idManager;
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
            element.setAttribute(ATTR_ID, idManager.getId(cv));
            element.setAttribute(ATTR_DIAGRAM, idManager.getId(cv.getDiagram()));
            element.setAttribute(ATTR_COMMENT, idManager.getId(cv.getComment()));
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
            element.setAttribute(ATTR_ID, idManager.getId(lv));
            element.setAttribute(ATTR_DIAGRAM, idManager.getId(lv.getDiagram()));
            element.setAttribute(ATTR_LINK, idManager.getId(lv.getLink()));
            root.addContent(element);
        }
        return root;
    }

    private Content getRelationshipViewsElement() {
        Element root = new Element(TAG_RELATIONSHIP_VIEWS);
        for(RelationshipView rv : diagram.getRelationshipViews()) {
            Element element = new Element(TAG_RELATIONSHIP_VIEW);
            element.setAttribute(ATTR_ID, idManager.getId(rv));
            element.setAttribute(ATTR_DIAGRAM, idManager.getId(rv.getDiagram()));
            element.setAttribute(ATTR_RELATIONSHIP, idManager.getId(rv.getRelationship()));
            root.addContent(element);
        }
        return root;
    }

    private Content getEntityViewsElement() {
        Element root = new Element(TAG_ENTITY_VIEWS);
        for(EntityView ev : diagram.getEntityViews()) {
            Element element = new Element(TAG_ENTITY_VIEW);
            element.setAttribute("id", idManager.getId(ev));
            element.setAttribute(ATTR_DIAGRAM, idManager.getId(ev.getDiagram()));
            element.setAttribute(ATTR_ENTITY, idManager.getId(ev.getEntity()));
            element.setAttribute(ATTR_X,String.valueOf(ev.getX()));
            element.setAttribute(ATTR_Y,String.valueOf(ev.getY()));
            root.addContent(element);
        }
        return root;
    }
}

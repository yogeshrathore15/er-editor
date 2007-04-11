package ru.amse.soultakov.ereditor.io.save;

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
        Element root = new Element("diagram");
        root.addContent(getEntityViewsElement());
        root.addContent(getCommentViewsElement());
        root.addContent(getLinkViewsElement());
        root.addContent(getRelationshipViewsElement());
        return root;
    }

    private Content getCommentViewsElement() {
        Element root = new Element("comment_views");
        for(CommentView cv : diagram.getCommentViews()) {
            Element element = new Element("comment_view");
            element.setAttribute("id", idManager.getStringId(cv));
            element.setAttribute("diagram", idManager.getStringId(cv.getDiagram()));
            element.setAttribute("comment", idManager.getStringId(cv.getComment()));
            element.setAttribute("x",String.valueOf(cv.getX()));
            element.setAttribute("y",String.valueOf(cv.getY()));
            root.addContent(element);
        }
        return root;
    }

    private Content getLinkViewsElement() {
        Element root = new Element("link_views");
        for(LinkView lv : diagram.getLinkViews()) {
            Element element = new Element("link_view");
            element.setAttribute("id", idManager.getStringId(lv));
            element.setAttribute("diagram", idManager.getStringId(lv.getDiagram()));
            element.setAttribute("link", idManager.getStringId(lv.getLink()));
            root.addContent(element);
        }
        return root;
    }

    private Content getRelationshipViewsElement() {
        Element root = new Element("relationship_views");
        for(RelationshipView rv : diagram.getRelationshipViews()) {
            Element element = new Element("relationship_view");
            element.setAttribute("id", idManager.getStringId(rv));
            element.setAttribute("diagram", idManager.getStringId(rv.getDiagram()));
            element.setAttribute("relationship", idManager.getStringId(rv.getRelationship()));
            root.addContent(element);
        }
        return root;
    }

    private Content getEntityViewsElement() {
        Element root = new Element("entity_views");
        for(EntityView ev : diagram.getEntityViews()) {
            Element element = new Element("entity_view");
            element.setAttribute("id", idManager.getStringId(ev));
            element.setAttribute("diagram", idManager.getStringId(ev.getDiagram()));
            element.setAttribute("entity", idManager.getStringId(ev.getEntity()));
            element.setAttribute("x",String.valueOf(ev.getX()));
            element.setAttribute("y",String.valueOf(ev.getY()));
            root.addContent(element);
        }
        return root;
    }
}

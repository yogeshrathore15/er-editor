package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_COMMENT;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ENTITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ID;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_LINK;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_RELATIONSHIP;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_X;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_Y;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_COMMENT_VIEWS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_ENTITY_VIEWS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_LINK_VIEWS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_RELATIONSHIP_VIEWS;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.controller.IProgressMonitor;
import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.Link;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.Diagram;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.LinkView;
import ru.amse.soultakov.ereditor.view.RelationshipView;

/**
 * @author Soultakov Maxim
 * 
 */
class DiagramLoader {

    private final LoadingIdManager loadingIdManager;

    private final Element diagramElement;

    public DiagramLoader(LoadingIdManager loadingIdManager, Element erModelElement) {
        this.loadingIdManager = loadingIdManager;
        this.diagramElement = erModelElement;
    }

    public Diagram load(IProgressMonitor monitor) {
        Diagram diagram = new Diagram();
        processEntities(diagram, diagramElement.getChild(TAG_ENTITY_VIEWS));
        monitor.setProgress(70);
        processComments(diagram, diagramElement.getChild(TAG_COMMENT_VIEWS));
        monitor.setProgress(80);
        processLinks(diagram, diagramElement.getChild(TAG_LINK_VIEWS));
        monitor.setProgress(90);
        processRelationships(diagram, diagramElement
                .getChild(TAG_RELATIONSHIP_VIEWS));
        monitor.setProgress(100);
        return diagram;
    }

    private void processEntities(Diagram diagram, Element element) {
        for (Object object : element.getChildren()) {
            if (object instanceof Element) {
                Element entityElement = (Element) object;
                String attributeValue = entityElement.getAttributeValue(ATTR_ENTITY);
                Entity entity = (Entity) loadingIdManager.getObject(attributeValue);
                int x = Integer.parseInt(entityElement.getAttributeValue(ATTR_X));
                int y = Integer.parseInt(entityElement.getAttributeValue(ATTR_Y));
                EntityView entityView = new EntityView(diagram, entity, x, y);
                loadingIdManager.putObject(entityElement.getAttributeValue(ATTR_ID),
                        entityView);
                diagram.addEntityView(entityView);
            }
        }
    }

    private void processComments(Diagram diagram, Element element) {
        for (Object object : element.getChildren()) {
            if (object instanceof Element) {
                Element commentElement = (Element) object;
                Comment comment = (Comment) loadingIdManager
                        .getObject(commentElement.getAttributeValue(ATTR_COMMENT));
                int x = Integer.parseInt(commentElement.getAttributeValue(ATTR_X));
                int y = Integer.parseInt(commentElement.getAttributeValue(ATTR_Y));
                CommentView commentView = new CommentView(diagram, comment, x, y);
                loadingIdManager.putObject(
                        commentElement.getAttributeValue(ATTR_ID), commentView);
                diagram.addCommentView(commentView);
            }
        }
    }

    private void processLinks(Diagram diagram, Element element) {
        for (Object object : element.getChildren()) {
            if (object instanceof Element) {
                Element linkElement = (Element) object;
                Link link = (Link) loadingIdManager.getObject(linkElement
                        .getAttributeValue(ATTR_LINK));
                LinkView linkView = new LinkView(diagram, link);
                loadingIdManager.putObject(linkElement.getAttributeValue(ATTR_ID),
                        linkView);
                diagram.addLinkView(linkView);
            }
        }
    }

    private void processRelationships(Diagram diagram, Element element) {
        for (Object object : element.getChildren()) {
            if (object instanceof Element) {
                Element relationshipElement = (Element) object;
                String attributeValue = relationshipElement
                        .getAttributeValue(ATTR_RELATIONSHIP);
                Relationship relationship = (Relationship) loadingIdManager
                        .getObject(attributeValue);
                RelationshipView relationshipView = new RelationshipView(diagram,
                        relationship);
                loadingIdManager.putObject(relationshipElement
                        .getAttributeValue(ATTR_ID), relationshipView);
                diagram.addRelationshipView(relationshipView);
            }
        }
    }

}

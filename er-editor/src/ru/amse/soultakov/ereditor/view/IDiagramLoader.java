package ru.amse.soultakov.ereditor.view;

import ru.amse.soultakov.ereditor.model.ERModel;

public interface IDiagramLoader {

    Diagram loadDiagram() throws DiagramLoadingException;
    
    ERModel loadModel() throws DiagramLoadingException;

}

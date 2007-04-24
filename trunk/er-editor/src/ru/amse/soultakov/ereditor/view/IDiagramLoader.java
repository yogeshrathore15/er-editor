package ru.amse.soultakov.ereditor.view;

import ru.amse.soultakov.ereditor.controller.IProgressMonitor;
import ru.amse.soultakov.ereditor.model.ERModel;

public interface IDiagramLoader {

    public Diagram loadDiagram(IProgressMonitor ipm) throws DiagramLoadingException;
    
    ERModel loadModel(IProgressMonitor ipm) throws DiagramLoadingException;

}

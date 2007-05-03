package ru.amse.soultakov.ereditor.view;

import ru.amse.soultakov.ereditor.controller.IProgressMonitor;
import ru.amse.soultakov.ereditor.model.ERModel;

public interface IDiagramSaver {

    public void save(Diagram diagram, ERModel erModel, IProgressMonitor monitor)
            throws DiagramSavingException;

}

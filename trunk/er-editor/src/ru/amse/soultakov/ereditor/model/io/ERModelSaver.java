package ru.amse.soultakov.ereditor.model.io;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ru.amse.soultakov.ereditor.model.ERModel;

/**
 * @author Soultakov Maxim
 *
 */
public class ERModelSaver {

    private final ERModel erModel;

    public ERModelSaver(ERModel erModel) {
        this.erModel = erModel;
    }
    
    public void save() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // will be never thrown
        }
        
    }

    public ERModel getERModel() {
        return this.erModel;
    }

    public File getFile() {
        return this.file;
    }
    
}

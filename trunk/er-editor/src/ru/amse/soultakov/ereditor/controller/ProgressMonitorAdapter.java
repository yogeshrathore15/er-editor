package ru.amse.soultakov.ereditor.controller;

import java.awt.Component;

import javax.swing.ProgressMonitor;

public class ProgressMonitorAdapter implements IProgressMonitor {

    private final ProgressMonitor progressMonitor;
    
    public ProgressMonitorAdapter(Component component) {
        progressMonitor = new ProgressMonitor(component, "", "", 0,100);
    }
    
    public void done() {
    }

    public void setProgress(int progress) {
        progressMonitor.setProgress(progress);
    }

    public void setNote(String note) {
        progressMonitor.setNote(note);
    }

}

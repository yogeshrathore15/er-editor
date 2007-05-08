package ru.amse.soultakov.ereditor.controller;

public interface IProgressMonitor {

    void setProgress(int progress);

    void setNote(String note);

    void done();

}

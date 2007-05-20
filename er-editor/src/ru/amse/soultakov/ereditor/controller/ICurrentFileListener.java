package ru.amse.soultakov.ereditor.controller;

import java.io.File;

public interface ICurrentFileListener {

    void currentFileChanged(File newCurrentFile, File oldCurrentFile);

}

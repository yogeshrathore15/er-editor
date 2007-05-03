package ru.amse.soultakov.ereditor.controller.undo;

public interface CommandManagerListener {

    void commandInvoked();

    void commandUndone();

    void commandRedone();

}

package ru.amse.soultakov.ereditor.controller.undo;

public interface ICommand {

    public void doIt();

    public void undoIt();

}

package ru.soultakov.remotecontrol.core.exceptions;

public class NoSuchCommandException extends IllegalJobException {

    public NoSuchCommandException(String commandName) {
        super("Unknown command : " + commandName);
    }

}

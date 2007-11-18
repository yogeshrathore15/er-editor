package ru.soultakov.remotecontrol.core.exceptions;

public class NoSuchCommandException extends IllegalCommandException {

    public NoSuchCommandException(String commandName) {
        super("Unknown command : " + commandName);
    }

}

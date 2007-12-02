package ru.soultakov.remotecontrol.core.exceptions;

public class NoSuchInterpreterException extends IllegalJobException {

    public NoSuchInterpreterException(String message) {
        super("Unknown interpreter : " + message);
    }

}

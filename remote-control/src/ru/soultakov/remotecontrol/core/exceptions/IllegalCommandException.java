package ru.soultakov.remotecontrol.core.exceptions;

public class IllegalCommandException extends Exception {

    public IllegalCommandException() {
        super();
    }

    public IllegalCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCommandException(String message) {
        super(message);
    }

    public IllegalCommandException(Throwable cause) {
        super(cause);
    }

}

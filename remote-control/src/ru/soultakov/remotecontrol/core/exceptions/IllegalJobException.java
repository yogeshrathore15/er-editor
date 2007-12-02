package ru.soultakov.remotecontrol.core.exceptions;

public class IllegalJobException extends Exception {

    public IllegalJobException() {
        super();
    }

    public IllegalJobException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalJobException(String message) {
        super(message);
    }

    public IllegalJobException(Throwable cause) {
        super(cause);
    }

}

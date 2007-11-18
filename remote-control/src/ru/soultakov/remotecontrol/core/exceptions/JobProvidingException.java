package ru.soultakov.remotecontrol.core.exceptions;

public class JobProvidingException extends Exception {

    public JobProvidingException() {
        super();
    }

    public JobProvidingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobProvidingException(String message) {
        super(message);
    }

    public JobProvidingException(Throwable cause) {
        super(cause);
    }

}

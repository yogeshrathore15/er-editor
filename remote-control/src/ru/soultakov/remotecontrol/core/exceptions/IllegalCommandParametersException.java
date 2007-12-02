package ru.soultakov.remotecontrol.core.exceptions;

public class IllegalCommandParametersException extends CommandExecutionException {

    public IllegalCommandParametersException() {
        super();
    }

    public IllegalCommandParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCommandParametersException(String message) {
        super(message);
    }

    public IllegalCommandParametersException(Throwable cause) {
        super(cause);
    }

}

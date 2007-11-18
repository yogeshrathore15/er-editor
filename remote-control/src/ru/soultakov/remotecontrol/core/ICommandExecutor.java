package ru.soultakov.remotecontrol.core;

import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalCommandException;

public interface ICommandExecutor {

    /**
     * ��������� �������, ���������� �������.
     * 
     * @param commandString
     * @throws IllegalCommandException
     * @throws CommandExecutionException
     */
    public abstract String execute(String commandString) throws IllegalCommandException,
            CommandExecutionException;

}
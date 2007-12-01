package ru.soultakov.remotecontrol.core;

import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalCommandException;

public interface IJobExecutionService {

    void start();

    void stop();

    public abstract void execute(IJob job) throws IllegalCommandException,
            CommandExecutionException;

}

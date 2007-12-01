package ru.soultakov.remotecontrol.core.impl.commands;

import java.util.List;
import java.util.Map;

import ru.soultakov.remotecontrol.Application;
import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;

public class StopCommand implements ICommand {

    @Override
    public String execute(Map<String, List<String>> parameters) throws CommandExecutionException {
        Application.getInstance().shutdown();
        return "Service stopped";
    }
}

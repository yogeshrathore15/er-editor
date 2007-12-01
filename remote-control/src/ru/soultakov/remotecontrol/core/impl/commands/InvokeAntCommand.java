package ru.soultakov.remotecontrol.core.impl.commands;

import java.util.List;
import java.util.Map;

import ru.soultakov.remotecontrol.ant.AntTaskInvokationException;
import ru.soultakov.remotecontrol.ant.AntTaskInvoker;
import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;

public class InvokeAntCommand implements ICommand {

    @Override
    public String execute(Map<String, List<String>> parameters) throws CommandExecutionException {
        final String task = parameters.get("task").get(0);
        try {
            return AntTaskInvoker.invokeTask(task);
        } catch (final AntTaskInvokationException e) {
            throw new CommandExecutionException(e.getMessage(), e);
        }
    }

}

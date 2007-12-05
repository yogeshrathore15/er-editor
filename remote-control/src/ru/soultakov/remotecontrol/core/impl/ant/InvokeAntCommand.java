package ru.soultakov.remotecontrol.core.impl.ant;

import java.util.List;
import java.util.Map;

import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalCommandParametersException;

public class InvokeAntCommand implements ICommand {

    private static final String TASK_KEY = "task";
    public static final String USAGE = "Usage: <some_tag> </some_tag";

    @Override
    public String execute(Map<String, List<String>> parameters) throws CommandExecutionException {
        final String task = getTask(parameters);
        try {
            return AntTaskInvoker.invokeTask(task);
        } catch (final AntTaskInvokationException e) {
            throw new CommandExecutionException(e.getMessage(), e);
        }
    }

    private String getTask(Map<String, List<String>> parameters)
            throws IllegalCommandParametersException {
        if (parameters == null) {
            throw new NullPointerException("Parameters is null");
        }
        final List<String> taskList = parameters.get(TASK_KEY);
        if ((taskList == null) || (taskList.size() == 0)) {
            throw new IllegalCommandParametersException(USAGE);
        }
        return taskList.get(0);
    }

}

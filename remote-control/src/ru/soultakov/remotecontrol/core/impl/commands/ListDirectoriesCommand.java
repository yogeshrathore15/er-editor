package ru.soultakov.remotecontrol.core.impl.commands;

import java.io.File;
import java.util.List;
import java.util.Map;

import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;

public class ListDirectoriesCommand implements ICommand {

    private static final String DIR = "dir";
    private static final String USAGE = "Incorrect usage. Available parameter: 'dir'";

    @Override
    public String execute(Map<String, List<String>> parameters) throws CommandExecutionException {
        validate(parameters);
        final String dir = parameters.get(DIR).get(0);
        final File file = new File(dir);
        final String[] fileNames = file.list();
        final StringBuilder sb = new StringBuilder();
        sb.append("Directories list : \n");
        for (final String entry : fileNames) {
            sb.append(entry).append('\n');
        }
        return sb.toString();
    }

    private void validate(Map<String, List<String>> parameters) throws CommandExecutionException {
        if (!parameters.containsKey(DIR)) {
            throw new CommandExecutionException(USAGE);
        }
    }
}

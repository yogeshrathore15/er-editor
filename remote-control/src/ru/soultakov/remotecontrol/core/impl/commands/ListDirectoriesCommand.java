package ru.soultakov.remotecontrol.core.impl.commands;

import java.io.File;
import java.util.List;
import java.util.Map;

import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;

public class ListDirectoriesCommand implements ICommand {

    @Override
    public String execute(Map<String, List<String>> parameters) throws CommandExecutionException {
        final String dir = parameters.get("dir").get(0);
        final File file = new File(dir);
        final String[] fileNames = file.list();
        final StringBuilder sb = new StringBuilder();
        sb.append("Directories list : \n");
        for (final String entry : fileNames) {
            sb.append(entry).append('\n');
        }
        return sb.toString();
    }

}

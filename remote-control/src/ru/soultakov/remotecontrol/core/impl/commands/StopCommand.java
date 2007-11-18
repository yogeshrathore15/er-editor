package ru.soultakov.remotecontrol.core.impl.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.Application;
import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.ICommandService;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;

public class StopCommand implements ICommand {

    private static final Logger LOGGER = Logger.getLogger(StopCommand.class);

    @Override
    public String execute(Map<String, List<String>> parameters) throws CommandExecutionException {
        final ICommandService commandService = Application.getCommandService();
        commandService.stop();
        LOGGER.info("Service stopped");
        return "Service stopped";
    }
}

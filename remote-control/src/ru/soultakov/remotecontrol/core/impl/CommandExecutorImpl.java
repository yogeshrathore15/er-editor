package ru.soultakov.remotecontrol.core.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.ICommandExecutor;
import ru.soultakov.remotecontrol.core.ICommandInterpreter;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalCommandException;
import ru.soultakov.remotecontrol.core.exceptions.NoSuchCommandException;

public class CommandExecutorImpl implements ICommandExecutor {

    private static final Logger LOGGER = Logger.getLogger(CommandExecutorImpl.class);

    private Map<String, ICommand> commands;
    private ICommandInterpreter interpreter;

    /**
     * {@inheritDoc}
     * 
     * @throws CommandExecutionException
     */
    public String execute(String commandString) throws IllegalCommandException,
            CommandExecutionException {
        LOGGER.info("Executing command '" + commandString + "'");
        final String commandName = interpreter.getName(commandString);
        final Map<String, List<String>> parameters = interpreter.getParameters(commandString);
        final ICommand command = commands.get(commandName);
        if (command == null) {
            throw new NoSuchCommandException(commandName);
        }
        return command.execute(parameters);
    }

    public void setCommands(Map<String, ICommand> commands) {
        this.commands = commands;
    }

    public Map<String, ICommand> getCommands() {
        return commands;
    }

    public void setInterpreter(ICommandInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public ICommandInterpreter getInterpreter() {
        return interpreter;
    }

}

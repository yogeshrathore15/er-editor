package ru.soultakov.remotecontrol.core.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.*;
import ru.soultakov.remotecontrol.core.exceptions.*;

public class JobExecutionServiceImpl implements IJobExecutionService {

    private static final Logger LOGGER = Logger.getLogger(JobExecutionServiceImpl.class);

    private volatile boolean stopped;

    private final ExecutorService executorService;
    private final Map<String, ICommand> commands;
    private final Map<String, ICommandInterpreter> interpreters;

    private final Object lock = new Object();

    public JobExecutionServiceImpl(ExecutorService executorService, Map<String, ICommand> commands,
            Map<String, ICommandInterpreter> interpreters) {
        this.executorService = executorService;
        this.commands = commands;
        this.interpreters = interpreters;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws CommandExecutionException
     */
    public void execute(final IJob job) throws IllegalJobException, CommandExecutionException {
        LOGGER.info("Preparing for executing job " + job);
        final ICommandInterpreter interpreter = interpreters.get(job.getInterpreterName());
        if (interpreter == null) {
            throw new NoSuchInterpreterException(job.getInterpreterName());
        }
        LOGGER.info("Interpreter has been got " + interpreter);
        final String commandName = interpreter.getName(job.getCommandText());
        final Map<String, List<String>> parameters = interpreter
                .getParameters(job.getCommandText());
        final ICommand command = commands.get(commandName);
        if (command == null) {
            throw new NoSuchCommandException(commandName);
        }
        LOGGER.info("Command has been created " + command);
        LOGGER.info("Command has been queued up for execution");
        executorService.execute(new CommandRunner(parameters, command, commandName, job));
    }

    @Override
    public void start() {
        checkState();
    }

    @Override
    public void stop() {
        synchronized (lock) {
            checkState();
            stopped = true;
            executorService.shutdown();
        }
    }

    private void checkState() {
        if (stopped) {
            throw new IllegalStateException("The service has already been stopped");
        }
    }

}

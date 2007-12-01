package ru.soultakov.remotecontrol.core.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.ICommandInterpreter;
import ru.soultakov.remotecontrol.core.IJob;
import ru.soultakov.remotecontrol.core.IJobExecutionService;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalCommandException;
import ru.soultakov.remotecontrol.core.exceptions.NoSuchCommandException;

public class JobExecutionServiceImpl implements IJobExecutionService {

    private static final Logger LOGGER = Logger.getLogger(JobExecutionServiceImpl.class);

    private volatile boolean stopped;

    private ExecutorService executorService;
    private Map<String, ICommand> commands;
    private ICommandInterpreter interpreter;

    /**
     * {@inheritDoc}
     * 
     * @throws CommandExecutionException
     */
    public void execute(final IJob job) throws IllegalCommandException, CommandExecutionException,
            NoSuchCommandException {
        final String commandName = interpreter.getName(job.getCommandText());
        final Map<String, List<String>> parameters = interpreter
                .getParameters(job.getCommandText());
        final ICommand command = commands.get(commandName);
        if (command == null) {
            throw new NoSuchCommandException(commandName);
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Executing job '" + commandName + "'");
                try {
                    final String result = command.execute(parameters);
                    job.done(result, true);
                } catch (final CommandExecutionException e) {
                    job.done(e.getMessage(), false);
                }
            }
        });
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

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        if (stopped) {
            throw new IllegalStateException("The service has already been stopped");
        }
        executorService.shutdown();
        stopped = true;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}

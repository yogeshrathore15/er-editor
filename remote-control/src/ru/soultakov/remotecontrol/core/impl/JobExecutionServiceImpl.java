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

    private ExecutorService executorService;
    private Map<String, ICommand> commands;
    private Map<String, ICommandInterpreter> interpreters;

    /**
     * {@inheritDoc}
     * 
     * @throws CommandExecutionException
     */
    public void execute(final IJob job) throws IllegalJobException, CommandExecutionException {
        final ICommandInterpreter interpreter = interpreters.get(job.getInterpreterName());
        if (interpreter == null) {
            throw new NoSuchInterpreterException(job.getInterpreterName());
        }
        final String commandName = interpreter.getName(job.getCommandText());
        final Map<String, List<String>> parameters = interpreter
                .getParameters(job.getCommandText());
        final ICommand command = commands.get(commandName);
        if (command == null) {
            throw new NoSuchCommandException(commandName);
        }
        executorService.execute(new Runnable() {
            /**
             * Executes job
             */
            @Override
            public void run() {
                LOGGER.info("Executing job '" + commandName + "'");
                try {
                    final String result = command.execute(parameters);
                    LOGGER.info("Executed job '" + commandName + "'");
                    LOGGER.info("Result = '" + result + "'");
                    notifyJob(job, result, true);
                } catch (final CommandExecutionException e) {
                    notifyJob(job, e.getMessage(), false);
                } catch (final RuntimeException e) {
                    LOGGER.error(e.getMessage(), e);
                    final String errorMessage = "Runtime exception : " + e.getClass().getName()
                            + ". Message: " + e.getMessage();
                    notifyJob(job, errorMessage, false);
                }
            }

            private void notifyJob(final IJob job, final String result, boolean success) {
                try {
                    job.done(result, success);
                } catch (final RuntimeException e) {
                    LOGGER.error(e.getMessage(), e);
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

    public Map<String, ICommandInterpreter> getInterpreters() {
        return this.interpreters;
    }

    public void setInterpreters(Map<String, ICommandInterpreter> interpreters) {
        this.interpreters = interpreters;
    }
}

/**
 * 
 */
package ru.soultakov.remotecontrol.core.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.IJob;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;

final class CommandRunner implements Runnable {
    
    private static final Logger LOGGER = Logger.getLogger(CommandRunner.class);
    
    private final Map<String, List<String>> parameters;
    private final ICommand command;
    private final String commandName;
    private final IJob job;

    CommandRunner(Map<String, List<String>> parameters, ICommand command,
            String commandName, IJob job) {
        this.parameters = parameters;
        this.command = command;
        this.commandName = commandName;
        this.job = job;
    }

    /**
     * Executes job
     */
    @Override
    public void run() {
        LOGGER.info("Executing job '" + this.commandName + "'");
        try {
            final String result = this.command.execute(this.parameters);
            LOGGER.info("Executed job '" + this.commandName + "'");
            LOGGER.info("Result = '" + result + "'");
            notifyJob(this.job, result, true);
        } catch (final CommandExecutionException e) {
            notifyJob(this.job, e.getMessage(), false);
        } catch (final RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            final String errorMessage = "Runtime exception : " + e.getClass().getName()
                    + ". Message: " + e.getMessage();
            notifyJob(this.job, errorMessage, false);
        }
    }

    private void notifyJob(final IJob job, final String result, boolean success) {
        try {
            job.done(result, success);
        } catch (final RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
package ru.soultakov.remotecontrol.core.impl.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.IJobExecutionService;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalJobException;
import ru.soultakov.remotecontrol.core.impl.AbstractJobProvidingService;

public class ConsoleJobProvidingService extends AbstractJobProvidingService {

    private static final String ANT = "ant";
    private static final String DEFAULT = "default";

    private static final Logger LOGGER = Logger.getLogger(ConsoleJobProvidingService.class);

    private final IJobExecutionService jobExecutionService;
    private final long timeout;

    private Thread inputThread;

    private final Object lock = new Object();

    public ConsoleJobProvidingService(IJobExecutionService jobExecutionService, long timeout) {
        this.jobExecutionService = jobExecutionService;
        this.timeout = timeout;
    }

    private void startConsole() {
        final BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!inputThread.isInterrupted()) {
                    try {
                        System.out.println("<Enter the next command> ");
                        final String commandText = userIn.readLine();
                        final ConsoleJob consoleJob;
                        if (commandText.startsWith(ANT)) {
                            consoleJob = new ConsoleJob(commandText.substring(3), ANT, lock);
                        } else {
                            consoleJob = new ConsoleJob(commandText, DEFAULT, lock);
                        }
                        synchronized (lock) {
                            try {
                                jobExecutionService.execute(consoleJob);
                                while (!consoleJob.isDone()) {
                                    lock.wait(timeout);
                                }
                            } catch (final IllegalJobException e) {
                                LOGGER.error(e.getMessage(), e);
                            } catch (final CommandExecutionException e) {
                                LOGGER.error(e.getMessage(), e);
                            } catch (final InterruptedException e) {
                                LOGGER.info("The thread was interrupted while waiting "
                                        + "for the job completion");
                                Thread.currentThread().interrupt();
                            } finally {
                                lock.notifyAll();
                            }
                        }
                    } catch (final IOException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }, "Console input thread");
        inputThread.start();
        LOGGER.info("Console thread started");
    }

    @Override
    protected void doResume() {
    }

    @Override
    protected void doStart() {
        LOGGER.info("Starting console job providing service");
        startConsole();
        LOGGER.info("Console job providing service successfully started");
    }

    @Override
    protected void doStop() {
        LOGGER.info("Stopping console job providing service");
        synchronized (lock) {
            lock.notifyAll();
        }
        inputThread.interrupt();
        LOGGER.info("Console thread is interrupted");
        LOGGER.info("Console job providing service stopped");
    }

    @Override
    protected void doSuspend() {
    }

}

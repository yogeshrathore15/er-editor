package ru.soultakov.remotecontrol.core.impl.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.IJobExecutionService;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalCommandException;
import ru.soultakov.remotecontrol.core.impl.AbstractJobProvidingService;

public class ConsoleJobProvidingService extends AbstractJobProvidingService {

    private static final Logger LOGGER = Logger.getLogger(ConsoleJobProvidingService.class);

    private IJobExecutionService jobExecutionService;
    private long timeout;

    private Thread inputThread;

    private final Object lock = new Object();

    private void startConsole() {
        final BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!inputThread.isInterrupted()) {
                    try {
                        System.out.println("<Enter the next command> ");
                        final String commandText = userIn.readLine();
                        final ConsoleJob consoleJob = new ConsoleJob(commandText, lock);
                        synchronized (lock) {
                            try {
                                jobExecutionService.execute(consoleJob);
                                // TODO : this is not correct!!
                                // But spurious wakeups appear so seldom..
                                // So, we can put a cock on this error
                                while (!consoleJob.isDone()) {
                                    lock.wait(timeout);
                                }
                            } catch (final IllegalCommandException e) {
                                LOGGER.error(e.getMessage());
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

    public IJobExecutionService getJobExecutionService() {
        return this.jobExecutionService;
    }

    public void setJobExecutionService(IJobExecutionService jobExecutionService) {
        this.jobExecutionService = jobExecutionService;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}

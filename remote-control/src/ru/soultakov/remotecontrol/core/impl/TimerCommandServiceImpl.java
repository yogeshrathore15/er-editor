package ru.soultakov.remotecontrol.core.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;

import ru.soultakov.remotecontrol.core.ICommandExecutor;
import ru.soultakov.remotecontrol.core.ICommandService;
import ru.soultakov.remotecontrol.core.IJobProvider;

public class TimerCommandServiceImpl implements ICommandService {

    private final Timer timer = new Timer();
    private final ExecuteCommandTask timerTask = new ExecuteCommandTask(this);
    private final Object lock = new Object();
    private volatile boolean started;
    private volatile boolean stopped;

    private ExecutorService executorService;
    private ICommandExecutor commandExecutor;
    private List<IJobProvider> jobProviders;

    private Long period;

    @Override
    public void start() {
        synchronized (lock) {
            checkState(false);
            timer.schedule(timerTask, 0, period);
            started = true;
        }
    }

    @Override
    public void suspend() {
        synchronized (lock) {
            checkState(true);
            if (started) {
                timerTask.setEnabled(false);
            }
        }
    }

    @Override
    public void wakeUp() {
        synchronized (lock) {
            checkState(true);
            timerTask.setEnabled(true);
        }
    }

    @Override
    public void stop() {
        synchronized (lock) {
            checkState(true);
            timer.cancel();
            executorService.shutdown();
            stopped = true;
        }
    }

    public ICommandExecutor getCommandExecutor() {
        return this.commandExecutor;
    }

    public void setCommandExecutor(ICommandExecutor executor) {
        this.commandExecutor = executor;
    }

    public Long getPeriod() {
        return period;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public void checkState(boolean mustBeStarted) {
        if (stopped) {
            throw new IllegalStateException("The service has already been stopped");
        }
        if (started != mustBeStarted) {
            throw new IllegalStateException(MessageFormat.format(
                    "The service has {0} already been started", mustBeStarted ? "not" : ""));
        }
    }

    public void setJobProviders(List<IJobProvider> jobProvider) {
        this.jobProviders = jobProvider;
    }

    public List<IJobProvider> getJobProviders() {
        return jobProviders;
    }

}

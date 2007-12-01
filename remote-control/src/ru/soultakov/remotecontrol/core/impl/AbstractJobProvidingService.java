package ru.soultakov.remotecontrol.core.impl;

import java.text.MessageFormat;

import ru.soultakov.remotecontrol.core.IJobProvidingService;

public abstract class AbstractJobProvidingService implements IJobProvidingService {

    private boolean stopped;
    private boolean started;

    private final Object lock = new Object();

    @Override
    public final void start() {
        synchronized (lock) {
            checkState(false);
            doStart();
            started = true;
        }
    }

    @Override
    public final void resume() {
        synchronized (lock) {
            checkState(true);
            doResume();
        }
    }

    @Override
    public final void suspend() {
        synchronized (lock) {
            checkState(true);
            doSuspend();
        }

    }

    @Override
    public final void stop() {
        synchronized (lock) {
            checkState(true);
            doStop();
            stopped = true;
        }
    }

    protected abstract void doStart();

    protected abstract void doSuspend();

    protected abstract void doResume();

    protected abstract void doStop();

    private void checkState(boolean mustBeStarted) {
        if (stopped) {
            throw new IllegalStateException("The service has already been stopped");
        }
        if (started != mustBeStarted) {
            throw new IllegalStateException(MessageFormat.format(
                    "The service has {0} already been started", mustBeStarted ? "not" : ""));
        }
    }

}

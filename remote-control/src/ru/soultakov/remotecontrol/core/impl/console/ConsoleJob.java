package ru.soultakov.remotecontrol.core.impl.console;

import ru.soultakov.remotecontrol.core.IJob;

public class ConsoleJob implements IJob {

    private final String commandText;
    private volatile boolean done = false;
    private final Object lock;

    public ConsoleJob(String commandText, Object lock) {
        this.commandText = commandText;
        this.lock = lock;
    }

    @Override
    public void done(String result, boolean success) {
        synchronized (lock) {
            System.out.print("The result of '" + commandText + "' is ");
            System.out.println(success + ". Result : " + result);
            done = true;
            lock.notifyAll();
        }
    }

    @Override
    public String getCommandText() {
        return commandText;
    }

    public boolean isDone() {
        return done;
    }

}

package ru.soultakov.remotecontrol.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ru.soultakov.remotecontrol.core.IJob;

public class TestJob implements IJob {

    private final String commandText;
    private final String expectedResult;
    private final boolean expectedSuccess;
    private String result;
    private boolean success;

    private final Object lock = new Object();
    private final String interpreterName;

    public TestJob(String commandText, String result, boolean success, String interpreterName) {
        this.commandText = commandText;
        this.expectedResult = result;
        this.expectedSuccess = success;
        this.interpreterName = interpreterName;
    }

    @Override
    public void done(String result, boolean success) {
        synchronized (lock) {
            this.result = result;
            this.success = success;
            lock.notifyAll();
            System.out.println("done");
        }
    }

    public void checkResult(long timeout) {
        synchronized (lock) {
            try {
                lock.wait(timeout);
                assertEquals(expectedResult, result);
                assertEquals(expectedSuccess, success);
            } catch (final InterruptedException e) {
                fail("Interrupted by " + e.getMessage());
            }
        }
    }

    @Override
    public String getCommandText() {
        return commandText;
    }

    public String getInterpreterName() {
        return this.interpreterName;
    }

}

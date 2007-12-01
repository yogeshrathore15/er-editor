package ru.soultakov.remotecontrol.core.impl;

import static junit.framework.Assert.assertEquals;
import ru.soultakov.remotecontrol.core.IJob;

public class TestJob implements IJob {

    private final String commandText;
    private final String expectedResult;
    private final boolean success;

    public TestJob(String commandText, String result, boolean success) {
        this.commandText = commandText;
        this.expectedResult = result;
        this.success = success;
    }

    @Override
    public void done(String result, boolean success) {
        assertEquals(this.success, success);
        assertEquals(result, expectedResult);
    }

    @Override
    public String getCommandText() {
        return commandText;
    }

}

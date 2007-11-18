package ru.soultakov.remotecontrol.core.impl.console;

import ru.soultakov.remotecontrol.core.IJob;

public class ConsoleJob implements IJob {

    private final String commandText;

    public ConsoleJob(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public void done(String result, boolean success) {
        System.out.print("The result of '" + commandText + "' is ");
        System.out.println(success + " :");
        System.out.println(result);
    }

    @Override
    public String getCommandText() {
        return commandText;
    }

}

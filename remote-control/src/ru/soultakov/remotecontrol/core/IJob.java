package ru.soultakov.remotecontrol.core;

public interface IJob {

    String getCommandText();

    void done(String result, boolean success);

}

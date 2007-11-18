package ru.soultakov.remotecontrol.core;

public interface ICommandService {

    void start();

    void suspend();

    void wakeUp();

    void stop();

}

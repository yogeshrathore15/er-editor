package ru.soultakov.remotecontrol.core;

public interface IJobProvidingService {

    void start();

    void suspend();

    void resume();

    void stop();

}

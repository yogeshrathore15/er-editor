package ru.soultakov.remotecontrol.core;

import java.util.List;

import ru.soultakov.remotecontrol.core.exceptions.JobProvidingException;

public interface IJobProvider {

    List<? extends IJob> getJobs() throws JobProvidingException;

}

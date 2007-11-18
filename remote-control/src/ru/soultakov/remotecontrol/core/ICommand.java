package ru.soultakov.remotecontrol.core;

import java.util.List;
import java.util.Map;

import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;

public interface ICommand {

    String execute(Map<String, List<String>> parameters) throws CommandExecutionException;

}

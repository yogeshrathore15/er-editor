package ru.soultakov.remotecontrol.core;

import java.util.List;
import java.util.Map;

import ru.soultakov.remotecontrol.core.exceptions.IllegalCommandException;

public interface ICommandInterpreter {

    String getName(String command) throws IllegalCommandException;

    Map<String, List<String>> getParameters(String command) throws IllegalCommandException;

}

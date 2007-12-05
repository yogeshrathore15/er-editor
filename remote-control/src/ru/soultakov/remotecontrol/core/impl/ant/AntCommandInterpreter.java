package ru.soultakov.remotecontrol.core.impl.ant;

import java.util.*;

import ru.soultakov.remotecontrol.core.ICommandInterpreter;
import ru.soultakov.remotecontrol.core.exceptions.IllegalJobException;

public class AntCommandInterpreter implements ICommandInterpreter {

    @Override
    public String getName(String command) throws IllegalJobException {
        return "ant";
    }

    @Override
    public Map<String, List<String>> getParameters(String command) throws IllegalJobException {
        return Collections.singletonMap("task", Arrays.asList(command));
    }

}
